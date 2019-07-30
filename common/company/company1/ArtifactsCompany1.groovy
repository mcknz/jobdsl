package jobdsl.common.company.in

import jobdsl.common.Artifact
import jobdsl.common.Automation
import jobdsl.common.constants.ArtifactName
import jobdsl.common.constants.Server

final class ArtifactsCompany1 implements Automation {

  ArtifactsIn() {
    artifacts = new ArrayList<Artifact>()

    artifacts.add(
      new BatchArtifactIn(
        ArtifactName.Company_Timeout,
        '[app path]/target',
        '[deploy path]',
        new ArrayList<Server>(
          Arrays.asList(Server.SERVER_NAME)
        ),
        false
      )
    )
  }

  List<Artifact> artifacts
}