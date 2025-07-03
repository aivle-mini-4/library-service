#!/bin/bash

echo "=== Kafka 이벤트 모니터링 ==="
echo "0. 종료"
echo "1. Docker Compose 시작"
echo "2. Docker Compose 정지"
echo "3. 모든 토픽 리스트 보기"
echo "4. 특정 토픽의 메시지 실시간 모니터링"
echo "5. 특정 토픽의 메시지 실시간 모니터링 (처음부터)"
echo "6. 특정 토픽의 메시지 히스토리 보기"
echo "7. 컨슈머 그룹 리스트 보기"
echo "8. 토픽 상세 정보 보기"
echo "9. 특정 토픽 재생성"
echo "10. 토픽 생성"
echo "11. 토픽 제거"
echo "=========================="

while true; do
    echo ""
    echo "=== Kafka 이벤트 모니터링 ==="
    echo "0. 종료"
    echo "1. Docker Compose 시작"
    echo "2. Docker Compose 정지"
    echo "3. 모든 토픽 리스트 보기"
    echo "4. 특정 토픽의 메시지 실시간 모니터링"
    echo "5. 특정 토픽의 메시지 실시간 모니터링 (처음부터)"
    echo "6. 특정 토픽의 메시지 히스토리 보기"
    echo "7. 컨슈머 그룹 리스트 보기"
    echo "8. 토픽 상세 정보 보기"
    echo "9. 특정 토픽 재생성"
    echo "10. 토픽 생성"
    echo "11. 토픽 제거"
    echo "12. 메뉴 다시 보기"
    echo "=========================="
    read -p "선택하세요 (0-11): " choice

    case $choice in
        0)
            echo "종료합니다."
            exit 0
            ;;
        1)
            echo "Docker Compose 시작 중..."
            cd infra
            docker-compose up -d
            cd ..
            echo "✅ Docker Compose 시작 완료!"
            ;;
        2)
            echo "실행 중인 Docker Compose 프로젝트:"
            cd infra
            running_composes=($(docker-compose ps --services 2>/dev/null | grep -v "No such service"))
            cd ..
            
            if [ ${#running_composes[@]} -eq 0 ]; then
                echo "실행 중인 Docker Compose 프로젝트가 없습니다."
            else
                echo "0. 모든 프로젝트 정지"
                for i in "${!running_composes[@]}"; do
                    echo "$((i+1)). ${running_composes[$i]}"
                done
                
                read -p "정지할 프로젝트 번호를 선택하세요 (0-${#running_composes[@]}): " compose_choice
                
                if [ "$compose_choice" = "0" ]; then
                    echo "모든 Docker Compose 프로젝트 정지 중..."
                    cd infra
                    docker-compose down
                    cd ..
                    echo "✅ 모든 프로젝트 정지 완료!"
                elif [ "$compose_choice" -ge 1 ] && [ "$compose_choice" -le ${#running_composes[@]} ]; then
                    selected_compose=${running_composes[$((compose_choice-1))]}
                    echo "'$selected_compose' 프로젝트 정지 중..."
                    cd infra
                    docker-compose stop "$selected_compose"
                    cd ..
                    echo "✅ '$selected_compose' 프로젝트 정지 완료!"
                else
                    echo "잘못된 선택입니다."
                fi
            fi
            ;;
        3)
            echo "모든 토픽 리스트:"
            docker exec kafka kafka-topics --list --bootstrap-server localhost:9092
            ;;
        4)
            read -p "모니터링할 토픽 이름을 입력하세요: " topic
            echo "토픽 '$topic'의 실시간 메시지 모니터링 (현재 시간 이후부터, Ctrl+C로 종료):"
            docker exec kafka kafka-console-consumer \
                --bootstrap-server localhost:9092 \
                --topic $topic \
                --group realtime-monitor-group
            ;;
        5)
            read -p "모니터링할 토픽 이름을 입력하세요: " topic
            echo "토픽 '$topic'의 실시간 메시지 모니터링 (처음부터, Ctrl+C로 종료):"
            docker exec kafka kafka-console-consumer \
                --bootstrap-server localhost:9092 \
                --topic $topic \
                --from-beginning \
                --group from-beginning-monitor-group
            ;;
        6)
            read -p "히스토리를 볼 토픽 이름을 입력하세요: " topic
            read -p "메시지 개수를 입력하세요 (기본값: 10): " count
            count=${count:-10}
            echo "토픽 '$topic'의 최근 $count개 메시지:"
            docker exec kafka kafka-console-consumer \
                --bootstrap-server localhost:9092 \
                --topic $topic \
                --max-messages $count \
                --from-beginning
            ;;
        7)
            echo "컨슈머 그룹 리스트:"
            docker exec kafka kafka-consumer-groups --bootstrap-server localhost:9092 --list
            ;;
        8)
            read -p "상세 정보를 볼 토픽 이름을 입력하세요: " topic
            echo "토픽 '$topic' 상세 정보:"
            docker exec kafka kafka-topics --describe --bootstrap-server localhost:9092 --topic $topic
            ;;


        9)
            read -p "초기화할 토픽 이름을 입력하세요: " topic
            echo "⚠️  경고: 토픽 '$topic'의 모든 메시지를 삭제하고 재생성합니다!"
            read -p "정말 수행하시겠습니까? (y/N): " confirm
            
            if [[ $confirm == [yY] || $confirm == [yY][eE][sS] ]]; then
                # 1. 토픽 설정 백업
                echo "토픽 설정 백업 중..."
                partitions=$(docker exec kafka kafka-topics --describe \
                    --bootstrap-server localhost:9092 --topic $topic | \
                    awk '/PartitionCount/ {print $2}')
                replication=$(docker exec kafka kafka-topics --describe \
                    --bootstrap-server localhost:9092 --topic $topic | \
                    awk '/ReplicationFactor/ {print $3}')
                
                # 2. 토픽 삭제
                echo "토픽 '$topic' 삭제 중..."
                docker exec kafka kafka-topics --delete \
                    --bootstrap-server localhost:9092 --topic $topic
                
                # 3. 삭제 완료 대기 (폴링 방식)
                echo "삭제 완료 대기 중... (최대 60초)"
                timeout=60
                while (( timeout > 0 )); do
                    if ! docker exec kafka kafka-topics --list \
                        --bootstrap-server localhost:9092 | grep -wq "$topic"; then
                        break
                    fi
                    sleep 5
                    ((timeout -= 5))
                done
                
                # 4. 토픽 재생성
                if (( timeout > 0 )); then
                    echo "토픽 재생성 중..."
                    docker exec kafka kafka-topics --create \
                        --bootstrap-server localhost:9092 \
                        --topic $topic \
                        --partitions ${partitions:-1} \
                        --replication-factor ${replication:-1}
                else
                    echo "⚠️  경고: 토픽 삭제가 완료되지 않아 재생성을 건너뜁니다."
                fi
                
                # 5. 컨슈머 그룹 초기화
                echo "컨슈머 그룹 오프셋 초기화 중..."
                groups=$(docker exec kafka kafka-consumer-groups \
                    --bootstrap-server localhost:9092 --list)
                for group in $groups; do
                    docker exec kafka kafka-consumer-groups \
                        --bootstrap-server localhost:9092 \
                        --group $group \
                        --reset-offsets --to-earliest \
                        --topic $topic --execute &> /dev/null
                done
                
                echo "✅ 토픽 '$topic' 초기화 완료!"
            else
                echo "작업이 취소되었습니다."
            fi
            ;;

        10)
            read -p "생성할 토픽 이름을 입력하세요: " topic
            read -p "파티션 개수를 입력하세요 (기본값: 1): " partitions
            partitions=${partitions:-1}
            read -p "리플리케이션 팩터를 입력하세요 (기본값: 1): " rf
            rf=${rf:-1}
            echo "'$topic' 토픽을 생성합니다..."
            docker exec kafka kafka-topics \
                --create \
                --bootstrap-server localhost:9092 \
                --topic $topic \
                --partitions $partitions \
                --replication-factor $rf
            ;;
        11)
            read -p "제거할 토픽 이름을 입력하세요: " topic
            echo "⚠️  경고: 토픽 '$topic'이 완전히 삭제됩니다!"
            read -p "정말 삭제하시겠습니까? (y/N): " confirm
            if [[ $confirm == [yY] || $confirm == [yY][eE][sS] ]]; then
                docker exec kafka kafka-topics --delete --bootstrap-server localhost:9092 --topic $topic
                echo "토픽 '$topic'이 삭제 명령되었습니다."
            else
                echo "삭제가 취소되었습니다."
            fi
            ;;

        *)
            echo "잘못된 선택입니다."
            ;;
    esac
done
