package jobdsl.common.company

import jobdsl.common.Artifact
import jobdsl.common.Automation
import jobdsl.common.company.company1.ArtifactsCompany1

final class CompanyAutomation implements Automation {
  CompanyAutomation() {
    artifacts = new ArrayList<Artifact>()

    for ( Artifact artifact: new ArtifactsCompany1().artifacts) {
      artifacts.add(artifact)
    }
  }
  List<Artifact> artifacts
}
