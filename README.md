# Comfort_soft_task

спулить проект: git clone <https://github.com/tatarchch/Comfort_soft_task.git>
перейти в директорию: cd Comfort-soft-task
сбилдить образ: docker build -t comfort-soft-app .
запустить контейнер: docker run -d -p 8181:8181 --name soft-app comfort-soft-app

отрыть swagger в браузере http://localhost:8181/swagger-ui/index.html#
