def dockerBuild(String svcName, String dockerVersionTag)
{
    try {
        sh "docker build -f ./${PROJECT_NAME}.${svcName}/Dockerfile -t ${convertSvcNameToDockerFullImageTag(svcName, dockerVersionTag)} .";
        echo "Docker Image Build Successfully Completed!";
    } catch(err) {
        echo "Docker Image Failed to Build!";
        throw err;
    }
}

def dockerPush(String svcName, String dockerVersionTag)
{
    try {
        sh "docker tag ${convertSvcNameToDockerImageTag(svcName)} ${DOCKER_REPO_NAME}/${convertSvcNameToDockerFullImageTag(svcName, dockerVersionTag)}";
        sh "docker push ${DOCKER_REPO_NAME}/${convertSvcNameToDockerFullImageTag(svcName, dockerVersionTag)}";
        echo "Docker Image Pushed to Repo Successfully!";
    } catch(err) {
        echo "Docker Failed to Push the Image to Repo!";
        throw err;
    }
}

def convertSvcNameToDockerImageTag(String svcName)
{
    return svcName.replace(".", "-").toLowerCase();
}

def convertSvcNameToDockerFullImageTag(String svcName, String dockerVersionTag)
{
    String projectName = PROJECT_NAME.toLowerCase();
    return "${projectName}.${convertSvcNameToDockerImageTag(svcName)}:${dockerVersionTag}"
}

def deployHelmChart(String svcName, String environment)
{
    String rootFolderName = "${svcName.split("\\.")[1]}s"
    String folderName = svcName.split("\\.")[0];
    String deployEnv = environment.toLowerCase();

    sh "helm upgrade -i -f .DevOps/${rootFolderName}/${folderName}/environments/values-${deployEnv}.yaml ${convertSvcNameToDockerImageTag(svcName)} . --wait"
}