
<img width="100" height="100" src="https://github.com/user-attachments/assets/3ebb960d-5ace-4f6c-8fd3-5cc2a19d855d"/>

## 알죠 - 그룹 알람 서비스

![image](https://github.com/user-attachments/assets/7c5ccc88-c5fe-47fd-a696-d71fa24d8b4a)

### 🤔  Why 알죠?

- 의미 있는 하루를 보내기 위해, ASAP이 열정 자극제가 되었으면 해요.
- 혼자보다는, 함께의 가치를 통해 위 목표를 이뤘으면 좋겠어요.
- 평소 놓치고 있던 일정은 없나요? 우리의 앱이 하루를 더 꼼꼼하게 만들어 줬으면 해요.
- 모임에서 또는 친구들과 함께 해야할 것이 있다면? “알죠”의 알람이 모두에게 울릴 수 있도록 책임질게요.
- 규칙적인 목표를 함께 계획하고 달성하여 성취감을 느낄 수 있도록 해요.

### 💁‍♂️  알죠 목표

- 혼자서 일어나는 것이 힘든 당신을 위해, 함께 해결할 수 있는 서비스를 제공해요.
- 본인의 일상 루틴을 공유하고 타인과의 공감대를 만들었으면 좋겠어요.
- 다양한 알람 해제 컨텐츠를 통해 사용자가 재미를 느꼈으면 좋겠어요.

### 🤲 팀 구성

- PM  1명
- 디자니어 2명
- Android 2명
- iOS 2명
- Backend 1명

### 👀 Preview
<p align="center">
  <img width=200 alt="splash" src="https://github.com/user-attachments/assets/21381732-c559-4a0d-a778-2d199c7b2311"/>
  <img width=200 alt="group-create" src="https://github.com/user-attachments/assets/85323233-8f4c-495c-b442-8ff52ddf4ca9"/>
  <img width=200 alt="group-details" src="https://github.com/user-attachments/assets/e9f5df7f-6e5f-42ad-9651-818e9a0b2916"/>
</p>
<br>
<p align="center">
  <img width=200 alt="group-details" src="https://github.com/user-attachments/assets/d1609248-e79c-464a-9833-9ff1ddadb095"/>
  <img width=200 alt="group-details" src="https://github.com/user-attachments/assets/81acb273-e354-4d87-a1bf-b1fd07f95399"/>
  <img width=200 alt="group-details" src="https://github.com/user-attachments/assets/e60faf48-93cf-407f-92dd-391a2a35c3aa"/>
  <img width=200 alt="group-details" src="https://github.com/user-attachments/assets/c11955c1-6fc0-4764-a7ac-fc4607be6658"/>
</p>

### 📋 Skills
<div>
<img src="https://img.shields.io/badge/Android Studio-6DB33F?style=flat-square&logo=android&logoColor=white"/>
<img src="https://img.shields.io/badge/Kotlin-967BDC?style=flat-square&logo=Kotlin&logoColor=white"/>
<img src="https://img.shields.io/badge/Firebase-FF9E0F?style=flat-square&logo=Firebase&logoColor=white"/>
<img src="https://img.shields.io/badge/Compose-6DB33F?style=flat-square&logo=jetpack&logoColor=white"/>
<img src="https://img.shields.io/badge/Hilt-2496ED?style=flat-square&logo=logoColor=white"/>
<img src="https://img.shields.io/badge/Coroutine-2496ED?style=flat-square&logo=logoColor=white"/>
<img src="https://img.shields.io/badge/Retrofit2-2496ED?style=flat-square&logo=logoColor=white"/>
<img src="https://img.shields.io/badge/Flow-2496ED?style=flat-square&logo=logoColor=white"/>
</div>

- - -
### 🏛️ Architecture
`Clean architecture pattern`을 적용하여 `Presentation, Domain, Data layer`로 모듈화하여 프로젝트를 설계하였습니다.
- `Domain module` 내에서 `repository interface`를 선언하고 `usecase`를 통해 `repository`의 함수를 실행합니다. 그 결과 값으로 `entity class`를 반환합니다.
- `Presentation module` 내부에서 정의한 `ViewModel`은 `Domain module`의 `usecase`에 대한 의존성을 가지고 `entity class`에 접근하여 UI를 구성합니다.
- `Data module` 내에서 `Domain module`에서 선언한 `Repository`에 대한 구현체를 생성하여 `local/remote datasource`에 접근 하여 필요한 요청(비동기 네트워크, 로컬 데이터베이스 접근 등)과 관련한 비즈니스 로직을 구현하였습니다.

<br>
<p align="center">
  <img width="639" alt="clean architecture" src="https://github.com/user-attachments/assets/2a5d4bb5-39be-4d9b-a7c0-a2e70c4317da" />
</p>

- - -
### 🖋️ Alarm flow

알죠 앱은 그룹 형태의 알람을 생성하여 같은 그룹원들에게 같은 시간에 알람을 울리는 서비스를 제공합니다. 그 과정에서 그룹원들의 알람 설정이 모두 상이할 수 있습니다. 때문에 알람 관련 정보를 서버에서 관리하도록 하고 `Firebase Cloud Messaging Server`와 서버를 연동해 사용자 이벤트에 의해 알람을 등록하도록 구현하였습니다.  

1. `Application` 시작 시 FCM token을 발급 받아 메모리 및 내부 DB에 저장합니다.
2. 알람 스케쥴링 위해 `Application`에서 발급 받은 `FCM token`과 그룹에 필요한 데이터들과 함께 그룹 생성 요청을 하게 됩니다.
3. 요청이 성공적으로 마무리되면 서버에서 알람 해제시 필요한 `Payload`와 함께 `FCM Server`로 알람 예약을 요청합니다.
4. 예약한 시간에 맞춰 `FCM Server`에서 `Application`으로 메세지를 보냅니다.
5. `Application`에서 Payload 내 정보에 맞는 알람음, 알람 해제 컨텐츠에 따라 사용자에게 알람을 표시합니다.
6. 사용자가 UI 상호작용을 통해 알람을 해제 합니다.
7. 서버에서 알람을 해제한 순서에 따라 점수를 부여해 그룹 내 랭킹 점수를 갱신합니다.  

<p align="center">
  <img width="826" alt="alarm-flow" src="https://github.com/user-attachments/assets/e7d8f3dd-bdc0-4d7e-b73f-a60c5553a367" />
</p>

> FCM은 1개월 내 사용하지 않는 유저에 대해 기존에 발급 했던 token을 만료 시킵니다. 따라서 새로운 token 값을 받을 때, 서버와 동기화 작업을 진행 해야 기존 알람도 정상적으로 울리게 됩니다.

- - -
### 🚗 Continuous Delivery
프로젝트를 진행하면서 기능을 구현하고 개발 단계에서 테스트를 자체적으로 진행하지만 기능적인 부분외에 다양한 케이스에서 테스트를 진행하기 위해서 다른 팀원의 테스트를 병행하는 것이 좋습니다. 하지만 로컬에서 빌드 후 테스터에게 전달하는 것은 비효율적인 방법이라고 생각했습니다. 

`github action`을 활용하여 main branch에 merge가 되면 `firebase app distribution`에 자동적으로 빌드 및 배포 작업이 진행되도록 연동하여 개발 단계에서 테스트 배포 프로세스를 간소화 하여 개발 시간을 확보할 수 있었습니다.


