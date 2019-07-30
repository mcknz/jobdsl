package jobdsl

import jobdsl.common.Artifact
import jobdsl.common.Automation
import jobdsl.common.Deployment
import jobdsl.common.Job
import jobdsl.common.Jobs
import jobdsl.common.constants.ArtifactType
import jobdsl.common.constants.Company
import jobdsl.common.constants.JobType
import javaposse.jobdsl.dsl.DslFactory
import jobdsl.dsl.BatchDeployDsl
import jobdsl.dsl.TriggerDsl
import jobdsl.dsl.ViewDsl

class AutomatedDeployment {

  private DslFactory factory
  private String jenkinsUrl
  private List<Artifact> artifacts
  private TriggerDsl triggerDsl
  private ViewDsl viewDsl
  private BatchDeployDsl batchDeployDsl
  private Map<Company, Map<ArtifactType, Jobs>> companyViews

  AutomatedDeployment(DslFactory factory, String jenkinsUrl, Automation automation) {
    Objects.requireNonNull(automation)
    this.factory = Objects.requireNonNull(factory)
    this.jenkinsUrl = Objects.requireNonNull(jenkinsUrl)
    this.artifacts = Objects.requireNonNull(automation.artifacts)

    triggerDsl = new TriggerDsl(factory)
    viewDsl = new ViewDsl(factory)
    batchDeployDsl = new BatchDeployDsl(factory)

    companyViews = new HashMap<>()
    initializeViews()
  }

  void create() {

    // create a trigger and deploy job for each artifact
    for (Artifact artifact : artifacts) {
      createTriggerJob(artifact)
      createDeployJob(artifact)
    }

    // create a Jenkins view with jobs for each company and artifact type (e.g., Company1 Batch)
    for(Map.Entry<Company, Map<ArtifactType, Jobs>> companyView : companyViews.entrySet()) {
      for(Map.Entry<ArtifactType, Jobs> viewEntry : companyView.getValue().entrySet()) {
        if(viewEntry.getValue().hasJobs) {
          viewDsl.create(companyView.getKey(), viewEntry.getKey(), viewEntry.getValue())
        }
      }
    }
  }

  // creates a trigger job and adds to view
  private void createTriggerJob(Artifact artifact) {
    Deployment deployment = getDeployment(artifact);
    Job triggerJob = new Job(deployment, JobType.TRIGGER)
    triggerDsl.create(triggerJob)
    addJobToView(triggerJob)
  }

  // creates a trigger job and adds to view
  private void createDeployJob(Artifact artifact) {
    Deployment deployment = getDeployment(artifact);
    Job deployJob = new Job(deployment, JobType.DEPLOY)
    if(artifact.type == ArtifactType.BATCH) {
      batchDeployDsl.create(deployJob)
    } else {
      //TODO: add onlineDeployDsl
    }
    addJobToView(deployJob)
  }

  // retrieves deployment object for a given artifact
  private Deployment getDeployment(Artifact artifact) {
    String name = getArtifactClassName(artifact)
    Class<?> c = Class.forName(name)
    // dynamically create instance using constructor
    return (Deployment)c.getConstructor(String, Artifact).newInstance(jenkinsUrl, artifact)
  }

  // build the fully qualified class name of the Deployment to be created:
  //  e.g., jobdsl.batch.company....
  private static String getArtifactClassName(Artifact artifact) {
    StringBuilder sb = new StringBuilder("jobdsl.")
    sb.append(artifact.type == ArtifactType.BATCH ? "batch" : "online")
    sb.append(".company.")
    sb.append(artifact.company.value())
    sb.append(".")
    sb.append(artifact.name.toString())
    sb.append("_")
    sb.append(artifact.type == ArtifactType.BATCH ? "Batch" : "Online")
    sb.append("Deployment")
    return sb.toString()
  }

  // defines all possible Jenkins views possible (all types, all companies)
  private initializeViews() {
    Map <ArtifactType, Jobs> artifactTypeViewJobs
    for(Company company : Company.values()) {
      artifactTypeViewJobs = new HashMap<>()
      artifactTypeViewJobs.put(ArtifactType.BATCH, new Jobs())
      artifactTypeViewJobs.put(ArtifactType.ONLINE, new Jobs())
      companyViews.put(company, artifactTypeViewJobs)
    }
  }

  // associates a given deployment job with the correct view
  private addJobToView(Job job) {
    Map<ArtifactType, Jobs> artifactTypeViewJobsMap = companyViews.get(job.company)
    artifactTypeViewJobsMap.get(job.artifactType).add(job)
  }
}
