import groovy.json.*

class QAMinds{
private static def getRootDir(def project){
		File projectFile= new File(project.getPath());
		return projectFile.getParentFile()
		}

public static def loadEnvConfig(def testSuite, def configName,def env, def log) {
			def project = testSuite.getProject()
			def configFile = new File(getRootDir(project), configName)
			log.info(configFile)
			def json = new JsonSlurper().parseText(configFile.text)
			json[env].each { key, val ->
			log.info("Setting Environment Variable: " + key + "->" + val)
			project.setPropertyValue(key, val)
			}
		}

public static def reportTestcaseResult(def testRunner, def resultFilePath, def log) {
			def project = testRunner.getTestCase().getTestSuite().getProject()
			def resultFile = new File(getRootDir(project), resultFilePath)
			def testCaseName = testRunner.getTestCase().getLabel()
			log.info("Creating report : " + resultFile)
			resultFile << 'RESULTS FOR ' + testCaseName+ '\n'
			def testStepsResults = testRunner.getResults()
			testStepsResults.each { stepResult ->
			def name = stepResult.getTestStep().getLabel()
			def status = stepResult.getStatus().toString()
			def time = stepResult.getTimeTaken().toString()
			def error = stepResult.getError().toString()
			resultFile << [name, status, time, error].join('::')+'\n'
		}
			log.info("Report completed")
		}
}
