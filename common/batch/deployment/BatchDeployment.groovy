package jobdsl.common.batch.deployment


import jobdsl.common.Deployment
import jobdsl.common.Parameter
import jobdsl.common.batch.BatchArtifact
import jobdsl.common.constants.batch.BatchDeployConstants

abstract class BatchDeployment extends Deployment {

  // Absolute url of the AMI_Integrations build artifacts
  private workspaceUrl

  /** Creates required artifact-specific data to create a batch deployment for use in a DSL script
   *
   * @param jenkinsUrl - Base Url of Jenkins host server
   * @param artifact - Represents an application artifact to be deployed
   */
  BatchDeployment(
    String jenkinsUrl,
    BatchArtifact artifact
  ) {
    super(jenkinsUrl, artifact)

    workspaceUrl = artifact.getWorkspaceUrl(jenkinsUrl)

    parameters.add(
      new Parameter(
        'ARTIFACT_PATH',
        artifactPath,
        'Absolute path of the artifact target folder in the Jenkins workspace.')
    )
    parameters.add(
      new Parameter(
        'DEPLOY_PATH',
        artifact.deployPath,
        'Path on batch server where the artifact is to be deployed.')
    )
    parameters.add(
      new Parameter(
        'ARTIFACT_URL',
        workspaceUrl + artifact.pathSuffix,
        'Absolute url of the artifact target folder in the Jenkins workspace.')
    )
    parameters.add(
      new Parameter(
        'BATCH_USER',
        BatchDeployConstants.BatchUser,
        'User designated to execute commands over SSH.')
    )
  }
}
