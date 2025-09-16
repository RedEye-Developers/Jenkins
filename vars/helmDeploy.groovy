def call(String helmFolderPath, String serviceName)
{
    sh 'helm upgrade -i ./${helmFolderPath}/values.yaml ${serviceName} ./$helmFolderPath --wait'
}