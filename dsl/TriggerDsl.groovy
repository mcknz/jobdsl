package jobdsl.dsl

import jobdsl.common.Job
import jobdsl.common.constants.BuildConstants
import javaposse.jobdsl.dsl.DslFactory

/**
 * Allows for programmatic creation of trigger jobs via JobDSL
 */
class TriggerDsl {

  private DslFactory factory

  /***
   * Creates jenkins job that triggers deployment job
   * @param factory - JobDsl factory that allows for creation of Jenkins objects
   */
  TriggerDsl(DslFactory factory) {
    this.factory = Objects.requireNonNull(factory)
  }

  /***
   * Creates a jenkins job that monitors the artifact target folder every ten minutes,
   *  and kicks off the deployment job if any changes are detected
   * @param job - artifact-specific data required to create the deployment job
   */
  void create(Job job) {

    factory.freeStyleJob(job.name) {

      disabled() //TODO: enable deployment when dev is complete

      description("Looks for changes in the " +
        "${job.artifactName.value()} artifacts folder, and kicks off the " +
        "${job.deployJobName} deployment job.")

      // sets the number of latest build log records to keep
      logRotator {
        numToKeep(BuildConstants.NumberOfLogsToKeep)
      }

      parameters {
        job.parameters.each { p ->
          stringParam(p.name, p.value, p.description)
        }
      }

      // Adds and configures the File System trigger to monitor
      //  the artifact path every 10 minutes for changes
      // NOTE: This requires FSTrigger Plugin
      configure { j ->
        j / 'triggers' << 'org.jenkinsci.plugins.fstrigger.triggers.FolderContentTrigger' {
          spec('''\
 #Every 10 minutes\
 \n\
 H/10 * * * *\
 ''')
          path(job.artifactPath)
          excludeCheckLastModificationDate(false)
          excludeCheckContent(false)
          excludeCheckFewerOrMoreFiles(false)
        }
      }

      steps {
        // adds and configures build step to call the deploy deployment
        //  and pass all current parameters to it
        // NOTE: This requires Parameterized Trigger Plugin
        // If plugin displays "You have no permission to build"
        //  this does not appear to be a correct error.
        //  See https://issues.jenkins-ci.org/browse/JENKINS-39444
        downstreamParameterized {
          trigger(job.deployJobName) {
            // Block until the triggered projects finish their builds
            block {
              buildStepFailure('FAILURE')
              failure('FAILURE')
              unstable('UNSTABLE')
            }
            parameters {
              currentBuild()
            }
          }
        }
      }
    }
  }
}
