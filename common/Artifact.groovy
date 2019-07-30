package jobdsl.common

import jobdsl.common.constants.*

abstract class Artifact {

  /***
   * Represents an application artifact to be deployed
   * @param type - type of built artifact (e.g., batch)
   * @param company - company the artifact belongs to
   * @param name - name of the built artifact as shown in pom file
   * @param path - The file path to target artifact location
   * @param serversToDeployTo - List of servers on which to deploy artifacts
   * @param isCompanyArtifact - determines if the artifact is in the company path
   */
  Artifact(ArtifactType type,
           Company company,
           ArtifactName name,
           String pathSuffix,
           List<Server> serversToDeployTo,
           boolean isCompanyArtifact
  ) {
    this.type = type
    this.company = company
    this.name = name
    this.pathSuffix = pathSuffix
    this.serversToDeployTo = Collections.unmodifiableList(serversToDeployTo)
    this.path = getPath(pathSuffix, isCompanyArtifact, company)
    this.credentialId = BuildConstants.CredentialId
  }

  final ArtifactType type
  final Company company
  final ArtifactName name
  final String pathSuffix
  final String path
  final List<Server> serversToDeployTo
  final String credentialId

  abstract String getPath(String suffix, boolean isCompanyArtifact, Company company)
}
