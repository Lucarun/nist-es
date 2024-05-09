.DEFAULT_GOAL=help
.PHONY: run build clean help
DIR := $(realpath $(dir $(realpath $(lastword $(MAKEFILE_LIST)))))
SS_DEPS := $(DIR)/stonesoup/dependencies
TARGET = install/elasticsearch-1.0.0.jar


run: $(TARGET) ## Run the test case

build: $(TARGET) ## Build the test case

$(TARGET): .installed
	cd src && ant \
	-Dstonesoup.database.postgres.required=no \
	-Dstonesoup.hibernate.postgres.required=no \
	-Dstonesoup.hibernate.mysql.required=no \
	-Dstonesoup.database.mysql.required=no \
	-Dstonesoup.socket.required=no -lib $(SS_DEPS)/java/stonesoup/lttng/lttng-stonesoup-0.1.jar \
	-Dstonesoup.socket.lib.dir="$(SS_DEPS)/java/stonesoup/socket" \
	-Dstonesoup.hibernate.mysql.lib.dir="$(SS_DEPS)/java/stonesoup/hibernate/mysql" \
	-Dstonesoup.hibernate.postgres.lib.dir="$(SS_DEPS)/java/stonesoup/hibernate/postgres" \
	-Dstonesoup.database.mysql.lib.dir="$(SS_DEPS)/java/stonesoup/database/mysql" \
	-Dstonesoup.database.postgres.lib.dir="$(SS_DEPS)/java/stonesoup/database/postgres" \
	-Dmaven.build.dir="$(DIR)/install" \
	-Dmaven.repo.local="$(SS_DEPS)/java/elasticsearch" \
	-Dmaven.settings.offline=true \
	-Dstonesoup.lttng.lib.dir="$(SS_DEPS)/java/stonesoup/lttng" \
	-Dstonesoup.lttng.dummy.lib.dir="$(SS_DEPS)/java/stonesoup/lttng-dummy" package

clean: ## Clean the test case
	rm -r install

.installed: ## Install the dependencies
	sh install-dependencies.sh

help: ## Show this help
	@grep -E '^[a-zA-Z0-9_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'
