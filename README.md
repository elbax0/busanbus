# 부산 버스 도착 정보 서비스
### [https://bbus.cc/](https://bbus.cc/)
부산 버스 정류장의 버스 도착 정보를 확인할 수 있는 서비스입니다.

## 기능
- 원하는 버스 정류장을 검색한 뒤 해당 버스 정류장의 버스 도착 정보를 확인할 수 있습니다.
- Naver Cloud Platform의 Maps 서비스를 이용해서 검색한 버스 정류장의 위치를 지도로 확인할 수 있습니다.
- 공공 데이터 포털의 '부산광역시_버스정보시스템' 오픈 API를 기반으로 각 버스 정류장의 버스 도착 정보를 확인할 수 있습니다.
- SSE(Server-Sent Events)를 적용하여 새로고침 없이 변경되는 도착 정보를 확인할 수 있습니다.

## API
- [https://www.data.go.kr/data/15092750/openapi.do](https://www.data.go.kr/data/15092750/openapi.do)
- [https://www.ncloud.com/product/applicationService/maps](https://www.ncloud.com/product/applicationService/maps)

## 사용한 기술
- Spring Boot
- Spring Webflux
- Spring Data JPA
- React
- MySQL
- Nginx
- AWS EC2