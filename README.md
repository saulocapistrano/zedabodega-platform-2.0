# zedabodega-platform-2.0
Sales management API with payments made, inventory control and delivery management, all with a tax invoice issuance module

```markdown
# ZeDaBodega Platform 2.0

ZeDaBodega Platform is a cloud-native microservices-based solution for managing small business operations. It offers robust capabilities for payment processing, inventory control, data synchronization, reporting, and system integration. Designed for scalability, high availability, and observability, this architecture follows industry best practices and leverages modern technologies.

---

## 📁 Monorepo Structure

```bash
zedabodega-platform-2.0/
├── ze-financeiro/           # Payment and financial transactions microservice
├── ze-estoque/              # Inventory and product stock control microservice
├── ze-relatorios/           # Reporting and dashboard microservice
├── ze-integrador-csv/       # Anti-Corruption Layer for spreadsheet conversion
├── shared-libs/             # Shared DTOs, common exceptions and security
├── infra/
│   ├── helm/                # Helm charts for Kubernetes deployments
│   │   └── ze-financeiro/
│   └── k8s/                 # Raw Kubernetes YAML files (optional use)
├── docs/                    # System documentation and diagrams
├── README.md                # This file
└── pom.xml                  # Maven parent configuration
```

---

## 🛠 Tech Stack

### Backend
- **Java 17**
- **Spring Boot 3.x**
- **Spring Web + Spring Data JPA**
- **Spring Kafka** – Event-driven communication
- **WebClient (Spring WebFlux)** – Non-blocking external API integration
- **PostgreSQL** – Relational data store
- **DynamoDB (AWS)** – NoSQL storage for unstructured data

### Messaging
- **Apache Kafka** – High-throughput event streaming
- **RabbitMQ** – Reliable transactional messaging with Dead Letter Queues (DLQ)

### Infrastructure & DevOps
- **Maven** – Multi-module build system
- **Docker** – Containerization
- **Helm** – Kubernetes package manager
- **AWS EKS (Elastic Kubernetes Service)** – Container orchestration
- **EC2 Spot Instances** – Cost-optimized compute
- **GitHub Actions** – CI/CD pipeline
- **AWS Secrets Manager** – Secure credentials management

### Observability & Monitoring
- **ELK Stack (Elasticsearch + Logstash + Kibana)** – Centralized logging
- **Prometheus + Grafana** – Metrics collection and visualization
- **Trace ID correlation** – Distributed tracing in logs

### Security
- **OAuth 2.0 + JWT** – Authentication and authorization
- **Spring Security** – Secure REST APIs
- **Environment-based Secrets Management** – via AWS or Docker secrets

### Data Import / Anti-Corruption Layer
- **Apache POI / OpenCSV** – Spreadsheet parsing
- **Custom Data Normalizer Service** – Converts legacy CSV/Excel into internal DTOs

### Performance & Testing
- **JUnit 5 + Mockito** – Unit and integration tests
- **JMeter** – Load and stress testing for microservices
- **Testcontainers** – Spinning real containers for integration tests

---

## 📦 Microservices Design

Each service in this monorepo represents a bounded context and owns its own data. Services communicate asynchronously through Kafka/RabbitMQ, and synchronously via REST/WebClient when necessary. Domain-driven design (DDD) patterns are applied for aggregates and consistency rules.

---

## 📈 Scalability & Availability

- **Horizontal scaling via Kubernetes (EKS)**
- **Service auto-restart via liveness/readiness probes**
- **Load balancing using Kubernetes Ingress**
- **Eventual consistency through asynchronous events**

---

## 🔐 Secure by Design

- All external-facing endpoints require authentication
- Token-based authorization using OAuth2 + JWT
- All secrets stored securely in cloud-native solutions
- Internal service calls are protected via internal API gateway and scoped roles

---

## 🧪 Quality Assurance

- 90%+ unit test coverage goal
- Contract tests for shared APIs (planned)
- Load testing scenarios for high-volume endpoints (e.g. Black Friday events)
- Static code analysis and vulnerability scanning (CI/CD stage)



## 👥 Contributing

> This project is structured for high maintainability and team collaboration. Please follow the established Git Flow strategy and commit conventions (`feat:`, `fix:`, `chore:`, etc.). PRs to `develop` only.

---

## 📄 License

This project is licensed under the MIT License. See the `LICENSE` file for details.

---

## 🔗 Contact

For inquiries or technical discussion, please open an issue or pull request in the repository.

```
