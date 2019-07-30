package jobdsl.common.online

import jobdsl.common.Artifact
import jobdsl.common.constants.BuildConstants
import jobdsl.common.constants.batch.BatchBuildConstants
import jobdsl.common.constants.ArtifactName
import jobdsl.common.constants.ArtifactType
import jobdsl.common.constants.Company
import jobdsl.common.constants.Server
import jobdsl.common.constants.online.PackageType

class OnlineArtifact extends Artifact {

  OnlineArtifact(Company company,
                 ArtifactName name,
                 String pathSuffix,
                 List<Server> serversToDeployTo,
                 PackageType packageType) {
    super(
      ArtifactType.ONLINE,
      company,
      name,
      pathSuffix,
      serversToDeployTo,
    true)
    this.packageType = packageType
  }

  PackageType packageType

  @Override
  String getPath(String suffix, boolean isCompanyArtifact, Company company) {
    return BuildConstants.WorkspaceCompanyPath +
      company + '/' +
      '/online/' +
      name.value() + '/' +
      packageType.value() +
      suffix
  }
}