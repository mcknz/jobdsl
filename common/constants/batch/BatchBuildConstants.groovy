package jobdsl.common.constants.batch

import jobdsl.common.constants.Company

class BatchBuildConstants {

  // Absolute base path on batch server where the artifact is to be deployed
  public static final String DeployPathBegin = '[root deployment path]'

  // Absolute url of the common build artifacts
  static final String getWorkspaceUrl(String jenkinsUrl, boolean isCompanyArtifact, Company company) {
    if(isCompanyArtifact) {
      return jenkinsUrl + 'deployment/[job name]/ws/[app root]/Company/' + company + '/batch/'
    } else {
      return jenkinsUrl + 'deployment/[job name]/ws/[app root]/Common/batch/'
    }
  }
}
