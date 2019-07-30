package jobdsl.batch.company.in

import jobdsl.common.Artifact
import jobdsl.common.batch.BatchArtifact
import jobdsl.common.batch.deployment.BatchDeployment

class Company_Events_BatchDeployment extends BatchDeployment {

  Company_Events_BatchDeployment(String jenkinsUrl, Artifact artifact) {
    super(jenkinsUrl, (BatchArtifact)artifact)
  }
}
