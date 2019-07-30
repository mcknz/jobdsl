package jobdsl.dsl

import javaposse.jobdsl.dsl.DslFactory
import jobdsl.common.Job
import jobdsl.common.constants.BuildConstants
import jobdsl.common.constants.online.OnlineBuildConstants

/**
 * Allows for programmatic creation of WebLogic deploy jobs via JobDSL
 */
class OnlineServerDeployDsl {

  private DslFactory factory

  /***
   * Creates jenkins job that deploys artifacts to WebLogic
   * @param factory - JobDsl factory that allows for creation of Jenkins objects
   */
  OnlineServerDeployDsl(DslFactory factory) {
    this.factory = Objects.requireNonNull(factory)
  }

  /***
   * Creates a jenkins deployment that monitors the artifact target folder every ten minutes,
   *  and kicks off the deployment deployment if any changes are detected
   * @param job - artifact-specific data required to create the deployment job
   */
  void create(Job job) {

    job.serversToDeployTo.each { server ->

      String serverName = server.value()

      factory.freeStyleJob(job.name + '-' + serverName) {

        disabled() //TODO: enable deployment when dev is complete

        description("Job for automated deployment to deploy {$job.artifactName} online artifacts (.war, .ear, etc) " +
          "to the WebLogic server {$serverName}.")

        // sets the number of latest build log records to keep
        logRotator {
          numToKeep(OnlineBuildConstants.NumberOfWebLogicLogsToKeep)
        }

        parameters {
          job.parameters.each { p ->
            stringParam(p.name, p.value, p.description)
          }
        }

        // Deploys an online artifact
        // NOTE: This requires Deploy WebLogic plugin
        publishers {
          deployToWeblogic {
            mustExitOnFailure()
            forceStopOnFirstFailure()
            task {
              weblogicEnvironmentTargetedName('dev_environment')
              deploymentName('myApplicationName')
              deploymentTargets('AdminServer')

              builtResourceRegexToDeploy('myApp\\.ear')
              taskName('Deploy myApp to DEV Server')

              jdkName('JDK_7')
              jdkHome('C:\\Program Files\\Java\\jdk1.7.0_65')

              stageMode(WeblogicDeploymentStageModes.STAGE)

              commandLine('-debug -remote -verbose -name {wl.deployment_name} -targets {wl.targets} ' +
                '-adminurl t3://{wl.host}:{wl.port} -user {wl.login} -password {wl.password} ' +
                '-undeploy -noexit;')
              commandLine('-debug -remote -verbose -name {wl.deployment_name} -source {wl.source} ' +
                '-targets {wl.targets}-adminurl t3://{wl.host}:{wl.port} -user {wl.login} ' +
                '-password {wl.password} -deploy -stage -upload;')
            }
          }
        }
      }
    }
  }
}
