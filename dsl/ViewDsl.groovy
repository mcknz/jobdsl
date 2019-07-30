package jobdsl.dsl

import javaposse.jobdsl.dsl.DslFactory
import jobdsl.common.Jobs
import jobdsl.common.constants.*

/**
 * Allows for programmatic creation of Jenkins views via JobDSL
 */
class ViewDsl {

  private DslFactory factory

  /***
   * Creates an instance suitable for creating Jenkins views
   * @param factory - JobDsl factory that allows for creation of Jenkins objects
   */
  ViewDsl(DslFactory factory) {
    this.factory = factory
  }

  /**
   * Creates a Jenkins view that contains jobs for a specified company and artifact type
   * @param company - represents a company
   * @param artifactType - determines if view contains batch or online jobs
   * @param viewJobs - list of jobs the view should contain
   */
  void create(Company company,
              ArtifactType artifactType,
              Jobs viewJobs) {

    String viewName = String.format(
      "Company %s - %s",
      company,
      artifactType.value())

    factory.listView(viewName) {
      configure({ v ->
        v / 'columns' << 'hudson.views.StatusColumn' {}
        v / 'columns' << 'hudson.views.WeatherColumn' {}
        v / 'columns' << 'hudson.views.JobColumn' {}
        v / 'columns' << 'hudson.views.LastSuccessColumn' {}
        v / 'columns' << 'hudson.views.LastFailureColumn' {}
        v / 'columns' << 'hudson.views.LastDurationColumn' {}
        v / 'columns' << 'hudson.views.BuildButtonColumn' {}
        v / 'columns' << 'org.jenkins.plugins.builton.BuiltOnColumn' {}
        v / 'columns' << 'hudson.plugins.favorite.column.FavoriteColumn' {}
      })
      jobs {
        viewJobs.jobList.each { job ->
          name(job.name)
        }
      }
    }
  }
}