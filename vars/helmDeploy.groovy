def call(String helmFolderPath, String serviceName, String deployMode)
{
    def buildEnv = deployMode == "Development" ? "dev" : "prod"

    try {
        sh "helm upgrade -i -f ./${helmFolderPath}/environments/values-${buildEnv}.yaml ${serviceName} ./$helmFolderPath --wait"
        echo "K8s Deployment Successfully Completed!"
    } catch (err) {
        echo "K8s Deployment Failed!"
        throw err
    }
}