package jobdsl.common.batch

import jobdsl.common.Artifact
import jobdsl.common.constants.batch.BatchBuildConstants
import jobdsl.common.constants.*

/***
 * Represents a batch application artifact to be deployed
 * @param company - company the artifact belongs to
 * @param name - name of the built artifact as shown in pom file
 * @param path - The file path to target artifact location
 * @param deployPathEnd - Path on batch server relative to deployPathBegin
 * @param serversToDeployTo - List of servers on which to deploy artifacts
 * @param isCompanyArtifact - determines if the artifact is in the company path
 */
class BatchArtifact extends Artifact {

  BatchArtifact(Company company,
                ArtifactName name,
                String pathSuffix,
                String deployPathSuffix,
                List<Server> serversToDeployTo,
                boolean isCompanyArtifact) {
    super(
      ArtifactType.BATCH,
      company,
      name,
      pathSuffix,
      serversToDeployTo,
      isCompanyArtifact)
    this.deployPath = BatchBuildConstants.DeployPathBegin + deployPathSuffix
    this.isCompanyArtifact = isCompanyArtifact
  }

  String deployPath
  boolean isCompanyArtifact

  String getWorkspaceUrl(String jenkinsUrl) {
    return BatchBuildConstants.getWorkspaceUrl(jenkinsUrl, isCompanyArtifact, company)
  }

  @Override
  String getPath(String suffix, boolean isCompanyArtifact, Company company) {
    if (isCompanyArtifact) {
      return BuildConstants.WorkspaceCompanyPath + company + '/batch/' + suffix
    } else {
      return BuildConstants.WorkspaceCommonPath + 'batch/' + suffix
    }
  }
}