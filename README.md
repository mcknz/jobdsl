# jobdsl
Groovy Jenkins framework for creating jobs using the JobDSL plugin

Seed DSL code:

```sh
import jobdsl.common.company.CompanyAutomation

new jobdsl.AutomatedDeployment(
  this,
  "${JENKINS_URL}",
  new CompanyAutomation()).create()
```
