kubectl delete service bakeryback
kubectl delete deployment bakeryback
./gradlew bootJar
eval $(minikube docker-env)
docker build --file=Dockerfile --tag=bakeryback:latest --rm=true .
kubectl create -f bakeryback_deployment.yml
minikube service bakeryback

