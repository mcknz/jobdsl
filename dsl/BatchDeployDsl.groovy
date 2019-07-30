package jobdsl.dsl

import javaposse.jobdsl.dsl.DslFactory
import jobdsl.common.Job
import jobdsl.common.constants.BuildConstants

/**
 * Allows for programmatic creation of Jenkins batch deployment jobs via JobDSL
 */
class BatchDeployDsl {

  private DslFactory factory

  /***
   * Creates an instance suitable for creating Jenkins batch deployment jobs
   * @param factory - JobDsl factory that allows for creation of Jenkins objects
   */
  BatchDeployDsl(DslFactory factory) {
    this.factory = Objects.requireNonNull(factory)
  }

  /***
   * Creates a jenkins deployment that deploys a batch application,
   *  and kicks off the deployment deployment if any changes are detected
   * @param job - artifact-specific data required to create the deployment
   */
  void create(Job job) {

    factory.pipelineJob(job.name) {

      disabled() //TODO: enable deployment when dev is complete

      description("Automated deploy pipeline deployment for the ${job.artifactName} batch application.")

      // sets the number of latest build log records to keep
      logRotator {
        numToKeep(BuildConstants.NumberOfLogsToKeep)
      }

      parameters {
        job.parameters.each { p ->
          stringParam(p.name, p.value, p.description)
        }
      }

      definition {
        cpsScm {
          scm {
            git {
              branch('*/develop')
              remote {
                credentials(job.credentialId)
                url('[url]')
              }
            }
          }
          scriptPath('batchDeployJenkinsfile')
        }
      }
    }
  }
}
