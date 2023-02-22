# ComposePractice
안드로이드 Jetpack Compose를 연습하기 위한 프로젝트 입니다  
구글의 강의(https://developer.android.com/courses/android-basics-compose/course?hl=ko)에 따라 공부하였습니다

### 공부한 내용
***23.02.20***  
> - 기본적인 Compose 사용방법 및 Material Design을 사용한 앱 제작 연습
> - Modifier의 기본적인 역할 공부
> - LazyColumn, LazyVerticalGrid를 통한 RecyclerView와 비슷한 기능 구현
  
> 문제해결  
> -> 문제 : Compose 1.2.0 부터 Scaffold 사용시 padding 값을 지정하지 않으면 "UnusedMaterialScaffoldPaddingParameter" 에러 발생  
> -> 해결방법 : Scaffold 안의 Compsable 메소드에 Modifier.padding(it)을 추가하여 해결 (it은 PaddingValues를 의미)
  
***23.02.21***
> - 지금까지 배운 내용을 가지고 앱 만들어보기 (코드랩 3-3-7)
> - 색상의 의미 앱을 만들었음
> - 사용한 기술 : LazyColumn, Scaffold - TopBar, animateContentSize, 앱 색상(테마)변경 등

***23.02.22***
> - Activity의 생명주기 [onCreate(), onStart(), onRestart(), onResume(), onPause(), onStop(), onDestroy()]
> - onDestroy() 후 재생성 되는 경우에도 값을 유지하려면 remember가 아닌 rememberSaveable을 사용해야됨
