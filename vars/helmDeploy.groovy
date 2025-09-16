def call(String helmFolderPath, String serviceName)
{
    try {
        sh 'helm upgrade -i ./${helmFolderPath}/values.yaml ${serviceName} ./$helmFolderPath --wait'
        echo "K8s Deployment Successfully Completed!"
    } catch (err) {
        echo "K8s Deployment Failed!"
        throw err
    }
}