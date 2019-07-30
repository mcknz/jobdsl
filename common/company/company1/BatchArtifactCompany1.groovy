package jobdsl.common.company.in

import jobdsl.common.batch.BatchArtifact
import jobdsl.common.constants.*

/***
 * Represents a company application artifact to be deployed
 * @param name - name of the built artifact as shown in pom file
 * @param path - The file path to target artifact location
 * @param deployPathEnd - Path on batch server relative to deployPathBegin
 * @param serversToDeployTo - List of servers on which to deploy artifacts
 * @param isCompanyArtifact - determines if the artifact is in the company path
 */
class BatchArtifactIn extends BatchArtifact {
  BatchArtifactIn(ArtifactName name,
                  String pathSuffix,
                  String deployPathSuffix,
                  List<Server> serversToDeployTo,
                  boolean isCompanyArtifact) {
    super(Company.Company1, name, pathSuffix, deployPathSuffix, serversToDeployTo, isCompanyArtifact)
  }
}