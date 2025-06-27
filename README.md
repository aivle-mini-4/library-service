# 

## Model
www.msaez.io/#/75278669/storming/192c7317e4862f82b5c01c664680d933

## Before Running Services
### Make sure there is a Kafka server running
```
cd kafka
docker-compose up
```
- Check the Kafka messages:
```
cd infra
docker-compose exec -it kafka /bin/bash
cd /bin
./kafka-console-consumer --bootstrap-server localhost:9092 --topic
```

## Run the backend micro-services
See the README.md files inside the each microservices directory:

- admintask
- monthlysubscriptionmanagement
- userinfomanagement
- authidentity
- pointmanagement
- scriptmanagement
- userhistorymanagement
- aiservice
- booksmanagement
- booksubstriptionmanagement


## Run API Gateway (Spring Gateway)
```
cd gateway
mvn spring-boot:run
```

## Test by API
- admintask
```
 http :8088/authorapprovals id="id"authorid="authorid"state="state"appliedAt="appliedAt"resultAt="resultAt"rejectedAt="rejectedAt"adminId="adminId"reason="reason"
 http :8088/pointpolicies id="id"name="name"description="description"pointType="pointType"amount="amount"isActive="isActive"createdAt="createdAt"updatedAt="updatedAt"
```
- monthlysubscriptionmanagement
```
 http :8088/subscribes id="id"name="name"isSubscribed="isSubscribed"updatedAt="updatedAt"
```
- userinfomanagement
```
 http :8088/writerProfiles id="id"name="name"email="email"roles="roles"basicInformation="basicInformation"selfIntroduction="selfIntroduction"portfolio="portfolio"updatedAt="updatedAt"
 http :8088/memberProfiles id="id"name="name"email="email"roles="roles"basicInformation="basicInformation"updatedAt="updatedAt"
 http :8088/adminProfiles id="id"name="name"email="email"roles="roles"
```
- authidentity
```
 http :8088/userAccounts id="id"email="email"password="password"roles="roles"createdAt="createdAt"updatedAt="updatedAt"
 http :8088/authorAccounts id="id"email="email"password="password"roles="roles"createdAt="createdAt"updatedAt="updatedAt"
 http :8088/adminAccounts id="id"email="email"password="password"roles="roles"createdAt="createdAt"updatedAt="updatedAt"
```
- pointmanagement
```
 http :8088/points id="id"userId="userId"points="points"history="history"
```
- scriptmanagement
```
 http :8088/manuscripts id="id"authorId="authorId"title="title"content="content"updatedAt="updatedAt"
```
- userhistorymanagement
```
 http :8088/viewHistories id="id"bookId="bookId"userId="userId"
 http :8088/favorites id="id"bookId="bookId"userId="userId"
```
- aiservice
```
 http :8088/aiServices id="id"manuscriptId="manuscriptId"model="model"apiKey="apiKey"
```
- booksmanagement
```
 http :8088/books id="id"authorId="authorId"coverImageUrl="coverImageUrl"title="title"summary="summary"category="category"price="price"content="content"views="views"
```
- booksubstriptionmanagement
```
 http :8088/bookSubscriptions id="id"name="name"bookId="bookId"isBookSubscribed="isBookSubscribed"updatedAt="updatedAt"
```


## Run the frontend
```
cd frontend
npm i
npm run serve
```

## Test by UI
Open a browser to localhost:8088

## Required Utilities

- httpie (alternative for curl / POSTMAN) and network utils
```
sudo apt-get update
sudo apt-get install net-tools
sudo apt install iputils-ping
pip install httpie
```

- kubernetes utilities (kubectl)
```
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
```

- aws cli (aws)
```
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install
```

- eksctl 
```
curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv /tmp/eksctl /usr/local/bin
```
