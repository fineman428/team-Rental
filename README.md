Applicatoin 구현

lombok 
플러그인 설치 
File -> settings -> Plugins -> Marketplace -> Lombok
플러그인 미인지시 
Settings -> Compiler -> Annotation Processors -> Enable annotation processing
메이븐 소스 다운로드


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

# aws config

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


# App gir 클론 받기
cd ~
mkdir project
git clone https://github.com/fineman428/team-Rental.git
git clone https://github.com/fineman428/team-Product.git
git clone https://github.com/fineman428/team-Information.git
git clone https://github.com/fineman428/team-gateway.git
git clone https://github.com/fineman428/team-Delivery.git


# 메이븐 lib 받기 
각 application 폴더별로 들어가서 
mvn package


# ECR Repository 만들기

# team-Delivery
Cd team-Delivery
aws ecr create-repository --repository-name skccuser02-delivery2 --image-scanning-configuration scanOnPush=true --region  ap-northeast-1
docker login --username AWS -p $(aws ecr get-login-password --region ap-northeast-1) 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-delivery
docker build -t 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-delivery2:v1 .
docker push 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-delivery2:v1

# team-information
Cd team-information
aws ecr create-repository --repository-name skccuser02-information --image-scanning-configuration scanOnPush=true --region  ap-northeast-1
docker login --username AWS -p $(aws ecr get-login-password --region ap-northeast-1) 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-information
docker build -t 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-information:v1 .
docker push 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-information:v1

# team-product
Cd team-product
aws ecr create-repository --repository-name skccuser02-product --image-scanning-configuration scanOnPush=true --region  ap-northeast-1
docker login --username AWS -p $(aws ecr get-login-password --region ap-northeast-1) 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-product
docker build -t 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-product:v1 .
docker push 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-product:v1

## team-Rental
cd team-Rental
aws ecr create-repository --repository-name skccuser02-rental --image-scanning-configuration scanOnPush=true --region  ap-northeast-1
docker login --username AWS -p $(aws ecr get-login-password --region ap-northeast-1) 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-rental
docker build -t 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-rental:v1 .
docker push 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-rental:v1
-메시지 no basic auth credentials

# team-gateway
Cd team-gateway
aws ecr create-repository --repository-name skccuser02-gateway --image-scanning-configuration scanOnPush=true --region  ap-northeast-1
docker login --username AWS -p $(aws ecr get-login-password --region ap-northeast-1) 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-gateway
docker build -t 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-gateway:v1 .
docker push 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-gateway:v1

# ECR 리파지토리 삭제
aws ecr delete-repository --repository-name skccuser02-delivery --force


# 네임스페이스 생성 
kubectl create namespace team-rent


# nano 설치 
apt-get install nano


### 헬름설치
EKS 클러스터 구성후 설치 가능

helm version
# 설치
sudo curl https://raw.githubusercontent.com/kubernetes/helm/master/scripts/get | bash

## Helm 초기화 설정
# helm 의 설치관리자를 위한 시스템 사용자 생성
kubectl --namespace kube-system create sa tiller
# 계정 롤 만들기
kubectl create clusterrolebinding tiller --clusterrole cluster-admin --serviceaccount=kube-system:tiller

helm init --service-account tiller
kubectl patch deploy --namespace kube-system tiller-deploy -p '{"spec":{"template":{"spec":{"serviceAccount":"tiller"}}}}'
helm repo add incubator http://storage.googleapis.com/kubernetes-charts-incubator
helm repo update

# helm으로 kafka 설치
- namespace
helm install --name my-kafka --namespace kafka incubator/kafka

# kafka 토픽 생성
kubectl -n kafka exec my-kafka-0 -- /usr/bin/kafka-topics --zookeeper my-kafka-zookeeper:2181 --topic rentalService --create --partitions 1 --replication-factor 1

# kafka 토픽 리스트 확인 
kubectl -n kafka exec my-kafka-0 -- /usr/bin/kafka-topics --zookeeper my-kafka-zookeeper:2181 --list


### 팀 과제 k8s deploy 띄우기 (no yaml )
# 올린 이미지를 통해 container 생성
# rental 
kubectl create deploy rental --image=496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-rental:v1 -n team-rent
kubectl expose deploy rental --port=8080 -n team-rent

# product 
kubectl create deploy product --image=496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-product:v1 -n team-rent
kubectl expose deploy product --port=8080 -n team-rent

# information
kubectl create deploy information --image=496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-information:v1 -n team-rent
kubectl expose deploy information --port=8080 -n team-rent

# delivery
kubectl create deploy delivery --image=496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-delivery2:v1 -n team-rent
kubectl expose deploy delivery --port=8080 -n team-rent

# gateway
kubectl create deploy gateway --image=496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-gateway:v1 -n team-rent
kubectl expose deploy gateway --type=LoadBalancer --port=8080 -n team-rent

kubectl delete deploy rental -n team-rent
kubectl delete service rental -n team-rent

### 팀 과제 k8s deploy 띄우기 (yaml )
 - deployment 생성 
   . kubectl create -f cm-deployment.yaml
 - Service 생성
   . kubectl create -f cm-service.yaml

Cd temp-Rental
kubectl create -f cm-deployment.yaml

# yaml 파일내 ID name 은 모두 소문자로 

# rental 
cd team-Rental/kubernetes
kubectl create -f deployment.yml -n team-rent
kubectl create -f service.yaml -n team-rent

# product
cd team-Product/kubernetes
kubectl create -f deployment.yml -n team-rent
kubectl create -f service.yaml -n team-rent

# information
cd team-Information/kubernetes
kubectl create -f deployment.yml -n team-rent
kubectl create -f service.yaml -n team-rent

# delivery
cd team-Delivery/kubernetes 
kubectl create -f deployment.yml -n team-rent
kubectl create -f service.yaml -n team-rent

# gateway
cd team-gateway/kubernetes
kubectl create -f deployment.yml -n team-rent
kubectl create -f service.yaml -n team-rent


======================
서비스 테스트 - team ver 2 
======================
## 데이터 조회
http GET http://localhost:8081/products/1

http GET http://product:8080/product
http GET http://product:8080/products

http http://Product:8080
http http://product:8080
http http://Rental:8080
http http://Delivery:8080
http http://Information:8080
http http://gateway:8080

http: error: ConnectionError: HTTPConnectionPool(host='product', port=8080): Max retries exceeded with url: / (Caused by NewConnectionError('<requests.packages.urllib3.connection.HTTPConnection object at 0x7fee7aa192d0>: Failed to establish a new connection: [Errno -2] Name or service not known',)) while doing GET request to URL: http://product:8080/



http a272043ee71fc482b9194feda4af0471-1504398459.ap-northeast-1.elb.amazonaws.com:8080/products



--상품등록
http a272043ee71fc482b9194feda4af0471-1504398459.ap-northeast-1.elb.amazonaws.com:8080/products name=Computer qty=9
http a272043ee71fc482b9194feda4af0471-1504398459.ap-northeast-1.elb.amazonaws.com:8080/products name=Monitor qty=9

--상품내역 확인
http a272043ee71fc482b9194feda4af0471-1504398459.ap-northeast-1.elb.amazonaws.com:8080/products/1
http a272043ee71fc482b9194feda4af0471-1504398459.ap-northeast-1.elb.amazonaws.com:8080/products/2
http a272043ee71fc482b9194feda4af0471-1504398459.ap-northeast-1.elb.amazonaws.com:8080/products


--정상대여
http a272043ee71fc482b9194feda4af0471-1504398459.ap-northeast-1.elb.amazonaws.com:8080/rentals productId=1 productName=Computer qty=3
http a272043ee71fc482b9194feda4af0471-1504398459.ap-northeast-1.elb.amazonaws.com:8080/rentals productId=2 productName=Monitor qty=3

http a272043ee71fc482b9194feda4af0471-1504398459.ap-northeast-1.elb.amazonaws.com:8080/rentals/1
http a272043ee71fc482b9194feda4af0471-1504398459.ap-northeast-1.elb.amazonaws.com:8080/rentals/2

http a272043ee71fc482b9194feda4af0471-1504398459.ap-northeast-1.elb.amazonaws.com:8080/deliveries/1
http a272043ee71fc482b9194feda4af0471-1504398459.ap-northeast-1.elb.amazonaws.com:8080/deliveries/2

--비정상대여
http a272043ee71fc482b9194feda4af0471-1504398459.ap-northeast-1.elb.amazonaws.com:8080/rentals productId=1 productName=Computer qty=10
http a272043ee71fc482b9194feda4af0471-1504398459.ap-northeast-1.elb.amazonaws.com:8080/rentals productId=2 productName=Monitor qty=10

http a272043ee71fc482b9194feda4af0471-1504398459.ap-northeast-1.elb.amazonaws.com:8080/rentals/3
http a272043ee71fc482b9194feda4af0471-1504398459.ap-northeast-1.elb.amazonaws.com:8080/rentals/4

http a272043ee71fc482b9194feda4af0471-1504398459.ap-northeast-1.elb.amazonaws.com:8080/deliveries/3
http a272043ee71fc482b9194feda4af0471-1504398459.ap-northeast-1.elb.amazonaws.com:8080/deliveries/4


http a272043ee71fc482b9194feda4af0471-1504398459.ap-northeast-1.elb.amazonaws.com:8080/rentals productId=100 productName=Computer qty=10
http a272043ee71fc482b9194feda4af0471-1504398459.ap-northeast-1.elb.amazonaws.com:8080/rentals productId=200 productName=Monitor qty=10

http a272043ee71fc482b9194feda4af0471-1504398459.ap-northeast-1.elb.amazonaws.com:8080/rentals/5
http a272043ee71fc482b9194feda4af0471-1504398459.ap-northeast-1.elb.amazonaws.com:8080/rentals/6

http a272043ee71fc482b9194feda4af0471-1504398459.ap-northeast-1.elb.amazonaws.com:8080/deliveries/5
http a272043ee71fc482b9194feda4af0471-1504398459.ap-northeast-1.elb.amazonaws.com:8080/deliveries/6

--대여내역
http a272043ee71fc482b9194feda4af0471-1504398459.ap-northeast-1.elb.amazonaws.com:8080/myOrders/



http DELETE a272043ee71fc482b9194feda4af0471-1504398459.ap-northeast-1.elb.amazonaws.com:8080/rentals/1



http a272043ee71fc482b9194feda4af0471-1504398459.ap-northeast-1.elb.amazonaws.com:8080/deliveries

