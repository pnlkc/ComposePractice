# ComposePractice
안드로이드 Jetpack Compose를 연습하기 위한 프로젝트 입니다  
구글의 강의(https://developer.android.com/courses/android-basics-compose/course?hl=ko)에 따라 공부하였습니다

### 공부한 내용
***ver 1.0.0***  
> - 기본적인 Compose 사용방법 및 Material Design을 사용한 앱 제작 연습
> - Modifier의 기본적인 역할 공부
> - LazyColumn, LazyVerticalGrid를 통한 RecyclerView와 비슷한 기능 구현
  
> 문제해결  
> -> 문제 : Compose 1.2.0 부터 Scaffold 사용시 padding 값을 지정하지 않으면 "UnusedMaterialScaffoldPaddingParameter" 에러 발생  
> -> 해결방법 : Scaffold 안의 Compsable 메소드에 Modifier.padding(it)을 추가하여 해결 (it은 PaddingValues를 의미)
