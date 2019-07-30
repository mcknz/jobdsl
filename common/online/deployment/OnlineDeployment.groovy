package jobdsl.common.online.deployment


import jobdsl.common.Deployment
import jobdsl.common.Parameter
import jobdsl.common.online.OnlineArtifact

abstract class OnlineDeployment extends Deployment {

  // Absolute url of the AMI_Integrations build artifacts
  private workspaceUrl

  /** Creates required artifact-specific data to create a batch deploy trigger deployment for use in a DSL script
   *
   * @param jenkinsUrl - Base Url of Jenkins host server
   * @param artifact - Represents an application artifact to be deployed
   */
  OnlineDeployment(
    String jenkinsUrl,
    OnlineArtifact artifact
  ) {
    super(jenkinsUrl, artifact)

    workspaceUrl = BatchBuildConstants.getWorkspaceUrl(
      jenkinsUrl, artifact.isCompanyArtifact, artifact.company
    )

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
        BatchJobConstants.BatchUser,
        'User designated to execute commands over SSH.')
    )
    parameters.add(
      new Parameter(
        'CREDENTIAL_ID',
        BatchJobConstants.CredentialId,
        'The Jenkins credential ID for SSH deployment.')
    )
  }
}
