#!/bin/bash

echo "=== Kafka 이벤트 모니터링 ==="
echo "1. 모든 토픽 리스트 보기"
echo "2. 특정 토픽의 메시지 실시간 모니터링"
echo "3. 특정 토픽의 메시지 히스토리 보기"
echo "4. 컨슈머 그룹 리스트 보기"
echo "5. 토픽 상세 정보 보기"
echo "6. 종료"
echo "=========================="

read -p "선택하세요 (1-6): " choice

case $choice in
    1)
        echo "모든 토픽 리스트:"
        docker exec kafka kafka-topics --list --bootstrap-server localhost:9092
        ;;
    2)
        read -p "모니터링할 토픽 이름을 입력하세요: " topic
        echo "토픽 '$topic'의 실시간 메시지 모니터링 (Ctrl+C로 종료):"
        docker exec kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic $topic --from-beginning
        ;;
    3)
        read -p "히스토리를 볼 토픽 이름을 입력하세요: " topic
        read -p "메시지 개수를 입력하세요 (기본값: 10): " count
        count=${count:-10}
        echo "토픽 '$topic'의 최근 $count개 메시지:"
        docker exec kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic $topic --max-messages $count --from-beginning
        ;;
    4)
        echo "컨슈머 그룹 리스트:"
        docker exec kafka kafka-consumer-groups --bootstrap-server localhost:9092 --list
        ;;
    5)
        read -p "상세 정보를 볼 토픽 이름을 입력하세요: " topic
        echo "토픽 '$topic' 상세 정보:"
        docker exec kafka kafka-topics --describe --bootstrap-server localhost:9092 --topic $topic
        ;;
    6)
        echo "종료합니다."
        exit 0
        ;;
    *)
        echo "잘못된 선택입니다."
        ;;
esac 