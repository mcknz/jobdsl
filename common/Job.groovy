package jobdsl.common

import jobdsl.common.constants.*

class Job {
  Job(Deployment deployment, JobType type) {
    this.company = deployment.company
    this.artifactName = deployment.artifactName
    this.artifactType = deployment.artifactType
    this.artifactPath = deployment.artifactPath
    this.type = type
    this.credentialId = deployment.credentialId
    this.serversToDeployTo = deployment.serversToDeployTo
    this.parameters = deployment.parameters
    //TODO: take out job name temporary name characters when dev complete
    this.name = String.format(
      "z-%s-%s-test",
      deployment.artifactName,
      type.value()
    )
    this.deployJobName = String.format(
      "z-%s-%s-test",
      deployment.artifactName,
      JobType.DEPLOY.value()
    )
    this.triggerJobName = String.format(
      "z-%s-%s-test",
      deployment.artifactName,
      JobType.TRIGGER.value()
    )
  }
  final Company company
  final ArtifactName artifactName
  final ArtifactType artifactType
  final String artifactPath
  final JobType type
  final String credentialId
  final List<Server> serversToDeployTo
  final List<Parameter> parameters
  final String name
  final String deployJobName
  final String triggerJobName
}
