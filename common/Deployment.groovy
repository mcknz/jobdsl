package jobdsl.common

import jobdsl.common.constants.*

abstract class Deployment {

  /** Creates required artifact-specific data to create a deployment for use in a DSL script
   *
   * @param jenkinsUrl - Base Url of Jenkins host server
   * @param artifact - Represents an application artifact to be deployed
   */
  Deployment(
    String jenkinsUrl,
    Artifact artifact
  ) {

    this.company = artifact.company
    this.artifactType = artifact.type
    this.artifactName = artifact.name
    this.artifactPath = artifact.path
    this.credentialId = artifact.credentialId
    this.jenkinsUrl = jenkinsUrl
    this.serversToDeployTo = artifact.serversToDeployTo
    List<Parameter> params = new ArrayList<Parameter>()

    // use the string value of the Server enum
    List<String> serverNames = this.serversToDeployTo.collect({
      it.value()
    })

    params.add(
      new Parameter(
        'EMAILS_TO_NOTIFY',
        BuildConstants.EmailsToNotify.join(" "),
        'Space-delimited list of email addresses to notify on deploy events.')
    )
    params.add(
      new Parameter(
        'ARTIFACT_NAME',
        artifactName.value(),
        'name of the built artifact as shown in pom file.')
    )
    params.add(
      new Parameter(
        'SERVERS_TO_DEPLOY_TO',
        serverNames.join(" "),
        'Space-delimited list of servers on which to deploy artifacts.')
    )
    params.add(
      new Parameter(
        'CREDENTIAL_ID',
        credentialId,
        'The Jenkins credential ID for SSH deployment.')
    )

    this.parameters = Collections.unmodifiableList(params)
  }

  // company the deployment belongs to
  final Company company

  // name of the built artifact as shown in pom file
  final ArtifactName artifactName

  // type of the built artifact (e.g., batch)
  final ArtifactType artifactType

  // Absolute path of the artifact target folder in Jenkins workspace
  final String artifactPath

  // Jenkins credential used to access servers over SSH
  final String credentialId

  // Jenkins deployment parameter key/value/description
  final List<Server> serversToDeployTo

  // Jenkins deployment parameter key/value/description
  final List<Parameter> parameters

  // Base Url of Jenkins host server
  final String jenkinsUrl

}
