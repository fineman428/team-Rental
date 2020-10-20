팀 프로젝트 진행 가이드 (from 정석희 수석)

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


=========================================================================================================
### Liveness Probes 테스트 
deployment.yaml 파일 

 livenessProbe:
            httpGet:
              path: '/rentals' -> 비정상적인 URL로 수정 
              port: 8080
            initialDelaySeconds: 120

kubelet 이 서비스 이상 감지후 POD 재생성 과정 확인
터미널 추가 후
watch kubectl get all -n team-rent



=========================================================================================================
### Istio 설치
경로 : home/project

kubectl version ==> 16 이상이어야 Istio 설치 가능 (클래스터 생성 시 버전 입력하는 구문 있음.)

curl -L https://istio.io/downloadIstio | ISTIO_VERSION=1.7.1 TARGET_ARCH=x86_64 sh -
cd istio-1.7.1
export PATH=$PWD/bin:$PATH
istioctl install --set profile=demo

kubectl apply -f samples/addons

--모니터링 설정 변경
--Monitoring Server - Kiali : 기본 ServiceType 변경 : ClusterIP를 LoadBalancer 로..
kubectl edit svc kiali -n istio-system
:%s/ClusterIP/LoadBalancer/g
:wq!

--Tracing Server - Jaeger : 기본 ServiceType 변경 : ClusterIP를 LoadBalancer 로..
kubectl edit svc tracing -n istio-system
:%s/ClusterIP/LoadBalancer/g
:wq!

### deployment.yml 수정
template:
    metadata:
      labels:
        app: /* 서비스명 */
        version: v1
      annotations:
        sidecar.istio.io/inject: "true"

### 기존서비스 종료
kubectl delete deploy rental -n team-rent
kubectl delete service rental -n team-rent

kubectl delete deploy product -n team-rent
kubectl delete service product -n team-rent

kubectl delete deploy information -n team-rent
kubectl delete service information -n team-rent

kubectl delete deploy delivery -n team-rent
kubectl delete service delivery -n team-rent

### istio 사이드카 주입 및 yml 파일로 배포
경로 : /project# 
kubectl apply -f <(istioctl kube-inject -f team-Delivery/kubernetes/deployment.yml) -n team-rent
kubectl create -f team-Delivery/kubernetes/service.yaml -n team-rent

kubectl apply -f <(istioctl kube-inject -f team-Rental/kubernetes/deployment.yml) -n team-rent
kubectl create -f team-Rental/kubernetes/service.yaml -n team-rent

kubectl apply -f <(istioctl kube-inject -f team-Information/kubernetes/deployment.yml) -n team-rent
kubectl create -f team-Information/kubernetes/service.yaml -n team-rent

kubectl apply -f <(istioctl kube-inject -f team-Product/kubernetes/deployment.yml) -n team-rent
kubectl create -f team-Product/kubernetes/service.yaml -n team-rent

istioctl 에서 오류 발생 시
cd istio-1.7.1
export PATH=$PWD/bin:$PATH

kubectl get all -n team-rent
kubectl get all -n istio-system


### 수정 후 접속 정보 확인
kubectl get svc -n istio-system 했을 때 LoadBalancer 서비스 확인

LoadBalancer 서비스
kiali
tracing (=Jaeger)


분산추적 시스템(Jaeger) 접속 : EXTERNAL-IP :80
http://ab73914550f014fa89557b90712ca5d8-1464908327.ap-northeast-1.elb.amazonaws.com/

모니터링 시스템(Kiali) 접속 : EXTERNAL-IP:20001 (admin/admin)
http://a6d06cd19065441e2a03edad0e6d3c15-1954250921.ap-northeast-1.elb.amazonaws.com:20001/






========================================================================
### 성능 부하 준비
========================================================================
부하테스트 툴(Siege) 설치 및 Order 서비스 Load Testing 
# 설치
kubectl run siege --image=apexacme/siege-nginx -n team-rent 

# siege pod id 확인 
kubectl get all -n team-rent 
pod/siege-5c7c46b788-lbkqb

# siege 실행접속가이드 
==> 교육자료 : kubectl exec -it siege -c siege -n team-rent -- /bin/bash
kubectl exec -it pod/siege-5c7c46b788-lbkqb -c siege -n team-rent -- /bin/bash
  .. siege 접속


========================================================================
무정지 재배포
========================================================================

# ECR 신규 이미지 버전 생성
docker build -t 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-product:v2 .

docker login --username AWS -p $(aws ecr get-login-password --region ap-northeast-1) 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-product

docker push 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-product:v2


# 부하발생
siege -c5 -t20S -v --content-type "application/json" 'http://order:8080/orders POST {"productId": "1001", "qty":5}'
siege -c30 -t20S -v --content-type "application/json" 'http://order:8080/orders POST {"productId": "1001", "qty":5}'

siege -c30 -t120S -v --content-type "application/json" 'http://a50c56c30cabd4893a598b74e8529ec7-30210382.ap-northeast-1.elb.amazonaws.com:8080/products POST {"name": "Computer", "qty":9}'


## Roll out 새로운 버전 출시
- image 버전 업데이트 후 주석 추가  

# replicas 속성 변경 
nano deployment.yaml ( spec 아래 항목에 replicas: 5 속성 추가; 있으면 수정 )
kubectl apply -f deployment.yml -n team-rent

# image update : kubectl set image deploy nginx-deployment nginx=nginx:1.9.1
kubectl set image deploy product product=496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-product:v1 -n team-rent
kubectl set image deploy product product=496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-product:v2 -n team-rent
 - product= ... 에서 product 명은 deployment.yml 의 containers 의 name

# annotation : kubectl annotate deploy nginx-deployment kubernetes.io/change-cause='v2 is revisioned nginx:1.9.1'
kubectl annotate deploy product kubernetes.io/change-cause='v1 is revisioned product' -n team-rent
kubectl annotate deploy product kubernetes.io/change-cause='v2 is revisioned product' -n team-rent

# 변경 확인 
kubectl rollout history deploy deployment.apps/product -n team-rent



## Rollout - undo (Rollback)
  - Roll back 변경 이전 버전 복귀   
    . kubectl rollout undo deploy product -n team-rent
    
    
    

========================================================================
HPA  (오토스케일 아웃)
========================================================================

• 결제서비스에 대한 replica 를 동적으로 늘려주도록 HPA 를 설정한다. 설정은 CPU 사용량이 15프로를 넘어서면 replica 를 10개까지 늘려준다:
kubectl autoscale deploy product --min=1 --max=10 --cpu-percent=10 -n team-rent

kubectl get all -n team-rent
kubectl delete hpa product -n team-rent

• CB 에서 했던 방식대로 워크로드를 2분 동안 걸어준다.
siege -c30 -t120S -v --content-type "application/json" 'http://a50c56c30cabd4893a598b74e8529ec7-30210382.ap-northeast-1.elb.amazonaws.com:8080/products POST {"name": "Computer", "qty":9}'

• 오토스케일이 어떻게 되고 있는지 모니터링을 걸어둔다:
kubectl get deploy product -w -n team-rent
watch kubectl get deploy product -w -n team-rent
어느정도 시간이 흐른 후 (약 30초) 스케일 아웃이 벌어지는 것을 확인할 수 있다:


========================================================================
Lab. Timeout : Fail-Fast를 통한 Gateway, 또는 서비스 Caller 자원 보호
========================================================================


Timeout 테스트를 위해 CNA 과정에서 구현한 Order 마이크로서비스의  코드 보완 및 tutorial 네임스페이스에 배포
Service time delay를 위해, Order Aggregate(Order.java)에 저장전 Thread.sleep 코드 삽입

---------------------------
	    @PrePersist
	    public void onPrePersist(){  
	        try {
	            Thread.currentThread().sleep((long) (800 + Math.random() * 220));
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    수정후 cna-oder
	    mvn package
	    
부하를 만들기 위해 수정
변경된 소스 반영
mvn package
docker build -t 271153858532.dkr.ecr.ap-southeast-2.amazonaws.com/admin11-cna-order:v2 .
docker push 271153858532.dkr.ecr.ap-southeast-2.amazonaws.com/admin11-cna-order:v2

kubernetest

vi deployment.yml

# 기존꺼 삭제 
kubectl delete deploy order -n team-rent
# deploy 생성
kubectl create deploy order --image=271153858532.dkr.ecr.ap-southeast-2.amazonaws.com/admin11-cna-order:v2 -n team-rent
# 이미지 정상적으로 올라갔는지 확인 
kubectl get deploy -o wide -n tutorial

## siege 접속
kubectl exec -it pod/siege-5c7c46b788-nrxwb -c siege -n team-rent -- /bin/bash

--------------------------------

[error] A temporary resolution error for a50c56c30cabd4893a598b74e8529ec7-30210382.ap-northeast-1.elb.amazonaws.com
: Resource temporarily unavailable

---------------------------------
	    
	    
Docker image Build & Push
tutorial 네임스페이스에 Order v2 서비스 재배포  
kubectl apply -f - <<EOF
apiVersion: apps/v1
kind: Deployment
metadata:
  name: order
  namespace: tutorial
  labels:
    app: order
spec:
  replicas: 1
  selector:
    matchLabels:
      app: order
  template:
    metadata:
      labels:
        app: order
    spec:
      containers:
        - name: order
          image: IMAGE_FULL_REPOSITORY_URL/order:v2
          ports:
            - containerPort: 8080
          resources:
            limits:
              cpu: 500m
            requests:
              cpu: 200m
EOF
Order 서비스 생성
kubectl expose deploy order --port=8080 -n tutorial
Order 서비스 Timeout 설정 (Istio Gateway에서 Order 서비스로 라우팅 시) 
(pwd 로 현 위치가 /istio-tutorial/ 인지 확인)
nano customer/kubernetes/Gateway.yaml 오픈 후 마지막 행 다음에 타임아웃 설정이 포함된 아래 내용 추가
  - match:
    - uri:
        prefix: /orders
    route:
    - destination:
        host: order
        port:
          number: 8080
    timeout: 3s
(변경 내용 적용)
kubectl apply -f customer/kubernetes/Gateway.yml -n tutorial

## Order 서비스 Timeout 설정 (클라우드 내에서 Order 서비스로 라우팅시)
-----------------------------
kubectl apply -f - <<EOF
apiVersion: networking.istio.io/v1alpha3
kind: VirtualService
metadata:
  name: vs-order-network-rule
  namespace: tutorial
spec:
  hosts:
  - order
  http:
  - route:
    - destination:
        host: order
    timeout: 3s
EOF

붙여넣고 enter
-----------------------------

# 부하테스트 툴(Siege) 설치 및 Order 서비스 Load Testing 
# siege 싱행
kubectl run siege --image=apexacme/siege-nginx -n tutorial 
# siege 접속
kubectl exec -it siege -c siege -n tutorial -- /bin/bash
 . kubectl get all -n tutorial
 . siege 를 siege pod id로 수정 
   .. pod/siege-5c7c46b788-nrxwb

# 부하발생
siege -c5 -t20S -v --content-type "application/json" 'http://order:8080/orders POST {"productId": "1001", "qty":5}'
siege -c30 -t20S -v --content-type "application/json" 'http://order:8080/orders POST {"productId": "1001", "qty":5}'

# 파이널에서도 관련결과 리포트 필요
Order 서비스에 설정된 Timeout을 임계치를 초과하는 순간, Istio에서 서비스로의 연결을 자동 차단하는 것을 확인
. 부하발생 서킷 브레이크 
  붉은색으로 결과 출력 


=======================
###Lab. Retry : 
5xx 오류를 리턴받게 되면, Envoy Proxy에서 설정한 횟수만큼 대상 서비스를 재호출하여 일시적인 장애였는지를 다시 확인하는 Rule 
=======================

# 동기 호출은 


Order 서비스에 Retry Rule 추가 적용
------------------------------------------------------------------

kubectl apply -f - <<EOF
apiVersion: networking.istio.io/v1alpha3
kind: VirtualService
metadata:
  name: vs-order-network-rule
  namespace: tutorial
spec:
  hosts:
  - order
  http:
  - route:
    - destination:
        host: order
    timeout: 3s
    retries:
      attempts: 3
      perTryTimeout: 2s
      retryOn: 5xx,retriable-4xx,gateway-error,connect-failure,refused-stream
EOF

. 
------------------------------------------------------------------

# Delivery 서비스를 정지하고, 이를 동기호출하는 Order 서비스 API 호출
kubectl scale deploy delivery --replicas=0 -n tutorial
 . replicas 0 으로 조정시 node service 가 모두 사라짐

kubectl exec -it siege -c siege -n tutorial -- /bin/bash
kubectl exec -it pod/siege-5c7c46b788-nrxwb -c siege -n tutorial -- /bin/bash

# 테스트 
http http://order:8080/orders/ productId=1001 qty=5
http DELETE http://order:8080/orders/1 
 . 삭제시 delivery 동기 호출
 . delivery 가 off 상태
 . 500 에러 발생
 
# retry 기록 확인 
jaeger
 동일한 시간대에 4번 error 발생 내역 확인
 총 3번 retry 설정되어 있음
 최초 실행1회   + retry 3회 = 4회 
 
 
 
 

 


