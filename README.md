# team-Rental

# ubuntu update
sudo apt-get update 명령 후 
sudo apt-get upgrade 명령을 통해 업데이트를 하고 사용


# unzip 설치 
sudo apt install unzip

출처: <https://itsfoss.com/unzip-linux/> 


# AWS CLI 설치 (버전2)
현재 AWS CLI가 설치되어 있는 경우 설치한 버전을 확인합니다.

aws --version
설치가 되어 있으면 설치과정 Skip

버전 1.18.143 이상 또는 버전 2.0.50 이상이 설치되어 있지 않으면 AWS CLI 버전 2를 설치합니다. 다른 설치 옵션을 확인하거나 현재 설치된 버전 2를 업그레이드하려면 Linux에서 AWS CLI 버전 2 업그레이드를 참조하십시오.

sudo curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
sudo unzip awscliv2.zip
sudo ./aws/install


### 설치 eksctl
Linux에서 curl을 사용하여 eksctl을 설치하거나 업그레이드하려면
다음 명령으로 eksctl 최신 릴리스를 다운로드하여 압축 해제합니다.

sudo curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
 . 압축 해제된 이진 파일을 /usr/local/bin으로 옮깁니다.

sudo mv /tmp/eksctl /usr/local/bin
다음 명령으로 설치가 제대로 되었는지 테스트합니다.

eksctl version

### kuberctl  설치 work flowy
apt-get update && apt-get install -y apt-transport-https
curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | apt-key add -
echo "deb https://apt.kubernetes.io/ kubernetes-xenial main" | tee -a /etc/apt/sources.list.d/kubernetes.list
  . " 문자 주의 , 실제 입력된 값에는 " 없이 파일이 등록되어 있어야 함 
  . kubernetes.list 잘못 생성된 경우 경로 폴더에 접근하여 sudo nano 로 편집
 . work flowy 에서 붙여 넣을시 중간에 ? 문자 생성 확인  후 제거 
apt-get update
apt-get install -y kubectl


# AWS EKS 클러스터 생성
eksctl create cluster --name skccuser02-team --version 1.17 --nodegroup-name standard-workers --node-type t3.medium --nodes 3 --nodes-min 1 --nodes-max 3


# 
aws configure
   . Access key id : AKIAXHDEFLPIZHCJKIPE
   . Secret Access key : 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
   . region name : ap-northeast-2
   . out put format : json



[✖]  unexpected status "ROLLBACK_IN_PROGRESS" while waiting for CloudFormation stack "eksctl-user02-eks-cluster"
[ℹ]  fetching stack events in attempt to troubleshoot the root cause of the failure
[✖]  AWS::EC2::InternetGateway/InternetGateway: CREATE_FAILED – "Resource creation cancelled"
[✖]  AWS::EC2::VPC/VPC: CREATE_FAILED – "Resource creation cancelled"
[✖]  AWS::EC2::EIP/NATIP: CREATE_FAILED – "Resource creation cancelled"
[✖]  AWS::IAM::Role/ServiceRole: CREATE_FAILED – "API: iam:CreateRole User: arn:aws:iam::496278789073:user/Skccuser02 is not authorized to perform: iam:CreateRole on resource: arn:aws:iam::496278789073:role/eksctl-user02-eks-cluster-ServiceRole-1NGFMBRGIWYZH with an explicit deny"
[!]  1 error(s) occurred and cluster hasn't been created properly, you may wish to check CloudFormation console
[ℹ]  to cleanup resources, run 'eksctl delete cluster --region=ap-northeast-2 --name=user02-eks'
[✖]  waiting for CloudFormation stack "eksctl-user02-eks-cluster": ResourceNotReady: failed waiting for successful resource state


AmazonEKSClusterPolicy
AdministratorAccess
IAMFullAccess
AmazonEC2FullAccess
AmazonS3FullAccess

