# Containers platform (2:30h) 13:30 - 16:00
  * Intro and presentation (30')
  * Market fragmentation
  * Rancher platform
  * Practices

---

##  Intro and presentation

Microservices architecture requires new infrastructure components. This kind or architectures have to be prepared to massive dynamism, portability and extensibility. Current cloud platforms wasn't thought to provide this features.
A containers platform is prepared to accomplish that and we gonna show some of the principal components of it.

* Scheduler: It takes care of where you container will be deployed.
* Network: Overlay network to communicate containers between them avoiding port conflict. 
* Storage: Storage service to be able to share and persist the services data.
* Service discovery: Information about what and where services are running in the system.
* Service management: Manage access to the services in a HA and load balance scenarios.

---

### CloudNativeLandscape
<img src="https://raw.githubusercontent.com/cncf/landscape/master/landscape/CloudNativeLandscape_v0.9.3.jpg", style="height:45vh; background-color:white; float:center;"/>
The functionality is the most important not the product. 

---

###  Schedulers

A container scheduler is a piece of software that manages the server resources and deployment into them. It knows all the servers that provide resources (cpu and memory), how much they offer and how much resource are free. Based on the work load of every server, the scheduler will deploy the containers in the most free server, in order to manage the global load of the system.

Also, the scheduler is responsible to move or to rebalance the services if a server enter in maintenance mode or goes down, and to assure that the services are running in the scale that you define for them.

---

Principal schedulers services in the market:

<img src="day2/Containers_Platform/slides/images/scheduler.jpg", style="height:40vh; background-color:white; float:center;"/>
 

---

### Network

Network overlay is a service that provide the communication between containers independent from the hosts. It would generate a flat overlay network that provide an ip to every single conatiner wide all hosts.

It helps to avoid networking collision and/or overlap between all the services exposing services in your system. 

---

Principal network services in the market:

<img src="day2/Containers_Platform/slides/images/network.jpg", style="height:40vh; background-color:white; float:center;"/>

---

###  Storage

Storage service makes the tasks to manage and provide persintence to your services. It's specially important in statefull and/or db services that has to persist and share data.

Basically, you could choose between block storage, fs storage or object storage. 

---

Principal storage services in the market:

<img src="day2/Containers_Platform/slides/images/storage.jpg", style="height:40vh; background-color:white; float:center;"/>

---

### Service discovery

A system discovery is a distributed key/value service that knows all the information about our services. When you add a new service, it will be added to the system discovery, when you scale an already deployed service a new backend will be added or removed to the system discovery. 

Users or services could talk with service discovery in order to know how to communicate with a service, for examples which are the backends of a services??

System discovery is dependent of the scheduler you are using.

---

Principal Service discovery in the market:

<img src="day2/Containers_Platform/slides/images/service_discovery.jpg", style="height:40vh; background-color:white; float:center;"/>

---

### Service management

A service management is a platform service that provides access to the services, providing a load balancing and high availability features. 

The service management provides an indirection layer to access the services. It could manages a service with dynamic backends that provide it or the access to different versions of the service. 

Load balancer could be external or internal, depending the use of them.


---

Principal service management in the market:

<img src="day2/Containers_Platform/slides/images/service_management.jpg", style="height:40vh; background-color:white; float:center;"/>

---

## Rancher platform

Rancher is an opensource containers platform, designed to be open and avoid vendor locks. 
It provides a unified platform to manage different environment from hosts to microservices. 
It provides a full API and CLI to facilitate the automation and the integration with other pieces. 
it provides some useful services as user auth, registry management, ssl certs management,

---

### Schema

<img src="day2/Containers_Platform/slides/images/platform.jpg", style="height:40vh; background-color:white; float:center;"/>

---

### Environment

<img src="day2/Containers_Platform/slides/images/environment.jpg", style="height:40vh; background-color:white; float:center;"/>

--- 

### Schedulers

<img src="day2/Containers_Platform/slides/images/rancher-schedulers.jpg", style="height:40vh; background-color:white; float:center;"/>

---

### Network

<img src="day2/Containers_Platform/slides/images/rancher-network.jpg", style="height:40vh; background-color:white; float:center;"/>

---

### Storage

<img src="day2/Containers_Platform/slides/images/rancher-storage.jpg", style="height:40vh; background-color:white; float:center;"/>

---

### Service Discovery

<img src="day2/Containers_Platform/slides/images/rancher-SDiscovery.jpg", style="height:40vh; background-color:white; float:center;"/>

---

### Service Management

HAproxy or traefik as load balancer
<img src="day2/Containers_Platform/slides/images/rancher-SManagement.jpg", style="height:40vh; background-color:white; float:center;"/>

---

### Catalog

<img src="day2/Containers_Platform/slides/images/rancher-catalog.jpg", style="height:40vh; background-color:white; float:center;"/>

---

### Overview

* Admin
* Infrastructure
* Environments
* Stack
* Catalog
* API
 
---

### Anatomy of a stack

Services: one stack could have one or many service inside it. Every service is independent in terms of deployment or scale.
Definition by docker-compose.yml and rancher-compose.yml. Use of standard, dependent of scheduler.

External services: Reference for external services out of rancher. They are useful to communicate internal pieces to external one.

LoadBalancer: Service that provides and manage the acces to the services. The access is provided through the hosts ip's. One lb could manage the acces to one (layer 4) or many services (layer 7)

---

## Example

Create a hms service and access slides to show the concept.

---

docker-compose.yml
```
version: '2'
services:
  lb:
    image: rancher/lb-service-haproxy:v0.4.9
    ports:
    - 8080:8080/tcp
    labels:
      io.rancher.container.agent.role: environmentAdmin
      io.rancher.container.create_agent: 'true'
  web:
    image: rawmind/hms
    stdin_open: true
    tty: true
    labels:
      io.rancher.container.pull_image: always
```

---

rancher-compose.yml
```
version: '2'
services:
  lb:
    scale: 1
    start_on_create: true
    lb_config:
      certs: []
      port_rules:
      - priority: 1
        protocol: http
        service: hms
        source_port: 8080
        target_port: 8080
    health_check:
      healthy_threshold: 2
      response_timeout: 2000
      port: 42
      unhealthy_threshold: 3
      interval: 2000
  web:
    scale: 1
    start_on_create: true
```

---

## Practice 1

Get the app dockers created in the previous stages, deploy an stack/services into the platform and access them.

---

## Practice 2

Make some change in one of the services and upgrade the already deployed stack/services.
