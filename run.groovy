package jobdsl
// for debugging in IDE
import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.JobParent
import jobdsl.common.company.CompanyAutomation
import jobdsl.dsl.TriggerDsl

/*
println(BuildConstants.WorkspaceBasePath)

println(BatchBuildConstants.WorkspaceCommonPath)
println(BatchBuildConstants.WorkspaceCompanyPath)
println(BatchBuildConstants.getWorkspaceCommonUrl('[jenkins_url]'))
println(BatchBuildConstants.DeployPathBegin)

BatchDeployment deployment = new Company_Timeout_BatchDeployment('[jenkins_url]')
deployment.parameters.each {p ->
  println(p.name)
}
println("asd")

ViewDsl viewDsl
TriggerDsl batchDeployTriggerDsl
println(ArtifactName.GridSmart_CommandCenter_DemandReset.value)
*/

//Class<?> c = Class.forName("jobdsl.common.batch.company.company1.Company_Timeout_BatchDeployment")
//def i = c.getConstructor(String).newInstance("[jenkins_url]")
//println("asdf")

  //.newInstance(jenkinsUrl: "[jenkins_url]");

//import jobdsl.common.company.CompanyAutomation

AutomatedDeployment ad = new jobdsl.AutomatedDeployment(
  new JobParent() {
    @Override
    Object run() {
      return null
    }
  },
  "[jenkins_url]",
  new CompanyAutomation())

ad.create()
