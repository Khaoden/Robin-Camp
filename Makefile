.PHONY: docker-up docker-down test-e2e clean

docker-up:
	docker compose up -d --build
	@echo "Waiting for services to be healthy..."
	@timeout 60 bash -c 'until curl -f http://localhost:8080/healthz >/dev/null 2>&1; do sleep 2; done' || true
	@echo "Services are ready!"

docker-down:
	docker compose down -v

test-e2e:
	@if [ ! -f .env ]; then \
		echo "Error: .env file not found. Please copy .env.example to .env and fill in the values."; \
		exit 1; \
	fi
	@./e2e-test.sh

clean:
	docker compose down -v
	docker system prune -f

