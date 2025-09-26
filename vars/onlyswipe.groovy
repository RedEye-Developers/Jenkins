def dockerBuild(String svcName, String dockerImageName, String dockerVersionTag)
{
    try {
        sh "docker build -f ./${PROJECT_NAME}.${svcName}/Dockerfile -t ${convertSvcNameToDockerFullImageTag(svcName, dockerImageName, dockerVersionTag)} .";
        echo "Docker Image Build Successfully Completed!";
    } catch(err) {
        echo "Docker Image Failed to Build!";
        throw err;
    }
}

def dockerPush(String svcName, String dockerImageName, String dockerVersionTag)
{
    try {
        sh "docker tag ${PROJECT_NAME.toLowerCase()}.${dockerImageName} ${DOCKER_REPO_NAME}/${convertSvcNameToDockerFullImageTag(svcName, dockerImageName, dockerVersionTag)}";
        sh "docker push ${DOCKER_REPO_NAME}/${convertSvcNameToDockerFullImageTag(svcName, dockerImageName, dockerVersionTag)}";
        echo "Docker Image Pushed to Repo Successfully!";
    } catch(err) {
        echo "Docker Failed to Push the Image to Repo!";
        throw err;
    }
}

def deployHelmChart(String svcName, String dockerImageName, String environment)
{
    String serviceType = "${svcName.split("\\.")[1]}"
    String folderName = svcName.split("\\.")[0];
    String deployEnv = environment.toLowerCase();

    def rootFolderName
    switch(serviceType) {
        case "Api":
            rootFolderName = "Apis"
            break
        case "Grpc":
            rootFolderName = "Grpcs"
            break
        case "Consumer":
            rootFolderName = "Kafka-Consumers"
            break
        default:
            throw new IllegalArgumentException("Invalid ServiceType!")
    }

    sh "helm upgrade -i -f ./DevOps/Helm/${rootFolderName}/${folderName}/environments/values-${deployEnv}.yaml ${dockerImageName} ./DevOps/Helm/${rootFolderName}/${folderName}/ --wait"
}

def convertSvcNameToDockerFullImageTag(String svcName, String dockerImageName, String dockerVersionTag)
{
    return "${PROJECT_NAME.toLowerCase()}.${dockerImageName}:${dockerVersionTag}"
}

def deployInfraHelmChart(String folderName, String dockerImageName, String environment)
{
    String deployEnv = environment.toLowerCase();
    
    sh "helm upgrade -i -f ./DevOps/Helm/Infras/${folderName}/environments/values-${deployEnv}.yaml ${dockerImageName} ./DevOps/Helm/Infras/${folderName}/ --wait"
}