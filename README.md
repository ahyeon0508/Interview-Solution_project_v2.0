## 숭실대학교 소프트웨어학부 캡스톤디자인종합프로젝트

## 프로젝트명
- 새내기路(로) : AI기반 대입 모의 면접 솔루션

-----------------

### 1. 프로젝트의 개요
#### 1-1. 프로젝트 개발 배경
&nbsp;&nbsp;&nbsp;현재 대한민국의 대학 입시 제도에는 수시모집전형(학생부종합전형, 학생부교과전형 등), 정시모집전형 등 다양한 전형이 존재한다. 대다수의 대학들이 면접전형을 시행하고 있으며, 대학별 반영비율에 따라 최종합격의 당락을 좌우한다. 이에 대해 대입 수험생들은 면접전형에 부담감을 느끼고 있다.  
&nbsp;&nbsp;&nbsp;면접에 부담감을 느끼는 사람들을 대상으로 인공지능(artificial intelligence, 이하 AI) 모의 면접 서비스가 제공되고 있지만, 대부분 취업 준비생들을 대상으로 하고 있어 대입 수험생들을 위한 서비스는 부족한 실정이다.  
&nbsp;&nbsp;&nbsp;따라서 본 프로젝트의 목적은 대입 수험생들에게 AI 기술을 기반으로 하여 모의 면접 서비스를 제공하는 것이다. 이 서비스는 면접 시 사용자의 답변 내용과 태도를 분석하여 결과를 제공함으로써 면접 상황에서의 본인의 모습을 파악하고 면접능력을 개발하도록 도와준다.  

#### 1-2. 프로젝트 목표 및 주요 기능
#### 최종 목표 : AI 기술을 이용한 대입 면접 수험생용 모의 면접 및 면접 결과 분석 서비스 개발

#### 주요 기능
| 기능 | 역할 |
| ------ | ------ |
| 질문 | 모의 면접에 사용할 질문들을 제공 및 관리하는 기능 <br> - 미리 제공되는 질문 : 공통 질문, 학과별 질문 <br> - 사용자가 입력하는 질문 : 사용자 작성 질문, 교사에게 공유 받은 질문|
| 면접 기록 | 질문별 영상 녹화 기능 및 답변 내용을 대본으로 기록하는 기능 |
| 면접 분석 | - 태도 분석 : 분당 말하는 음절, 단어 수를 이용하여 말하기 속도를 분석하고, 면접 동안 사용자의 시선이 어디에 위치했는지 시선 분포 결과를 제공해주는 기능<br> - 답변 분석 : 답변 내용에서 감탄사(음, 어)와 반복어 빈도를 계산하여 보여주는 기능 |
| 공유 | 교사에게 영상 및 질문을 공유할 수 있는 기능 |
| 성적 시각화 | 성적을 입력하여 성적 추이 및 특징을 시각화하는 기능 |

### 2. 개발 환경 및 개발 언어
|| tool |
| ------ | ------ |
| 개발언어 | ![issue badge](https://img.shields.io/badge/Java-11-blue.svg) ![issue badge](https://img.shields.io/badge/javascript-blue.svg) |
| 라이브러리 & 프레임워크 | ![issue badge](https://img.shields.io/badge/Spring%20Framework-2.4.1-green.svg) ![issue badge](https://img.shields.io/badge/jQuery-green.svg) ![issue badge](https://img.shields.io/badge/Bootstrap-green.svg) ![issue badge](https://img.shields.io/badge/OpenCV-3.4.2-green.svg) |
| Open API | [ETRI 음성 인식 API](https://aiopen.etri.re.kr/guide_recognition.php) [ETRI 형태소 인식 API](https://aiopen.etri.re.kr/guide_wiseNLU.php) [커리어넷 학교 정보 오픈 API](https://www.career.go.kr/cnet/front/openapi/openApiMainCenter.do) |
| 개발환경 | Windows10 |
| 데이터베이스 환경 | ![issue badge](https://img.shields.io/badge/MySQL-8.0.11-lightgrey.svg) |

### 3. 시스템 구조도

&nbsp;&nbsp;&nbsp;[그림 1]은 본 프로젝트의 구조도이며 캡스톤디자인종합프로젝트1과 캡스톤디자인종합프로젝트2의 개발 내용이 담겨져 있다. 본 repository는 캡스톤디자인종합프로젝트2의 개발 내용로 구성되어 있으며, 캡스톤디자인종합프로젝트1의 개발 내용은 해당 깃허브[Interview-Solution_project_1.0](https://github.com/ahyeon0508/Interview-Solution_project_v1.0)를 참고한다.  
&nbsp;&nbsp;&nbsp;[그림 2]는 ER diagram이다.

<p align="center" style="color:gray">
  <img style="margin:50px 0 10px 0" src="https://user-images.githubusercontent.com/44939208/147386142-93d44fba-1c4b-4fe9-a112-f07c51235075.png" alt="구조도" width="50%" height="50%"  />
  <br>[그림 1] 시스템 구조도<br>
</p><br><br>

<p align="center" style="color:gray">
  <img style="margin:50px 0 10px 0" src="https://user-images.githubusercontent.com/44939208/135191384-d6129930-2b95-412c-9013-6bf2eeaa9f8a.png" alt="ER diagram" width="50%" height="50%"  />
  <br>[그림 2] ER diagram<br>
</p> 
 

### 4. 새내기路(로) 결과 화면 일부
  
<p align="center" style="color:gray">
  <img style="margin:50px 0 10px 0" src="https://user-images.githubusercontent.com/44939208/135192085-f0bb5955-1507-4430-bbbe-25f7fa4de0f2.png" alt="메인 화면" width="50%" height="50%"  />
  <br>[그림 3] 새내기路(로) 메인 페이지 <br>
</p> <br>

<p align="center" style="color:gray">
  <img style="margin:50px 0 10px 0" src="https://user-images.githubusercontent.com/44939208/103658966-42971200-4faf-11eb-9082-89f0c1db12a3.png" alt="학생 메인 화면" width="50%" height="50%"  />
  <br>[그림 4] 학생 메인 페이지 <br>
</p> <br>

<p align="center" style="color:gray">
  <img style="margin:50px 0 10px 0" src="https://user-images.githubusercontent.com/44939208/103658974-43c83f00-4faf-11eb-8409-449dccc56a3e.png" alt="교사 메인 화면" width="50%" height="50%"  />
  <br>[그림 5] 교사 메인 페이지 <br>
</p> <br>

<p align="center" style="color:gray">
  <img style="margin:50px 0 10px 0" src="https://user-images.githubusercontent.com/44939208/135201580-58090dc4-7b7c-4c24-a5fe-9cdff036e11f.png" alt="로그인 화면" width="50%" height="50%"  />
  <br>[그림 6] 로그인 페이지 <br>
</p> <br>

<p align="center" style="color:gray">
  <img style="margin:50px 0 10px 0" src="https://user-images.githubusercontent.com/44939208/103658976-4460d580-4faf-11eb-866f-2c52cdf1b513.png" alt="내 질문 리스트" width="50%" height="50%"  />
  <br>[그림 7] 내 질문 리스트 페이지 <br>
</p> <br>

<p align="center" style="color:gray">
  <img style="margin:50px 0 10px 0" src="https://user-images.githubusercontent.com/44939208/103658988-462a9900-4faf-11eb-94dc-63c21da82554.png" alt="면접 질문 설정 화면" width="50%" height="50%"  />
  <br>[그림 8] 면접 질문 설정 페이지 <br>
</p> <br>

<p align="center" style="color:gray">
  <img style="margin:50px 0 10px 0" src="https://user-images.githubusercontent.com/44939208/103658984-45920280-4faf-11eb-970f-9476ad90ff62.png" alt="면접 세팅 화면" width="50%" height="50%"  />
  <br>[그림 9] 면접 세팅 페이지 <br>
</p> <br>

<p align="center" style="color:gray">
  <img style="margin:50px 0 10px 0" src="https://user-images.githubusercontent.com/44939208/103658987-462a9900-4faf-11eb-8cad-b6d1a20ed568.png" alt="면접 진행 화면" width="50%" height="50%"  />
  <br>[그림 10] 면접 진행 페이지 <br>
</p> <br>

<p align="center" style="color:gray">
  <img style="margin:50px 0 10px 0" src="https://user-images.githubusercontent.com/44939208/135205673-dfed5825-da62-4577-8ef9-abac7eb40b1b.png" alt="면접 대기 화면" width="50%" height="50%"  />
  <br>[그림 11] 면접 대기 페이지 <br>
</p> <br>

<p align="center" style="color:gray">
  <img style="margin:50px 0 10px 0" src="https://user-images.githubusercontent.com/44939208/135201378-f161ff3b-e607-4234-b907-d046ea8adee7.png" alt="면접 리포트 화면" width="50%" height="50%"  />
  <br>[그림 12] 면접 리포트 페이지 <br>
</p> <br>

<p align="center" style="color:gray">
  <img style="margin:50px 0 10px 0" src="https://user-images.githubusercontent.com/44939208/103658972-43c83f00-4faf-11eb-8383-1675e99fd0be.png" alt="학생 면접 영상 화면" width="50%" height="50%"  />
  <br>[그림 13] 학생 면접 영상 페이지 <br>
</p> <br>

<p align="center" style="color:gray">
  <img style="margin:50px 0 10px 0" src="https://user-images.githubusercontent.com/44939208/103658991-46c32f80-4faf-11eb-8c61-4e2d626aec78.png" alt="예상 질문 전송 화면" width="50%" height="50%"  />
  <br>[그림 14] 예상 질문 전송 페이지 <br>
</p> <br>

<p align="center" style="color:gray">
  <img style="margin:50px 0 10px 0" src="https://user-images.githubusercontent.com/44939208/135192148-24e52d3a-5ade-4446-9427-fb13a095508f.png" alt="성적 시각화 화면" width="50%" height="50%"  />
  <br>[그림 15] 성적 시각화 페이지 <br>
</p> <br>

<p align="center" style="color:gray">
  <img style="margin:50px 0 10px 0" src="https://user-images.githubusercontent.com/44939208/135201689-b0eb120c-c50d-4695-a6d4-53f03269fa24.png" alt="서류 입력 화면" width="50%" height="50%"  />
  <br>[그림 16] 서류 입력 페이지 <br>
</p> <br>
  

### 5. 기대효과 및 활용방안
&nbsp;&nbsp;&nbsp;먼저, 새로운 비대면 대입 서비스 시장을 창출할 것이다. 국내 스마트러닝의 시장규모는 연평균 20.64%씩 성장하여 2022년에는 15조 3,945억 원에 달할 것으로 전망되고 있다. 이는 코로나 뿐만 아니라, 국가 정책하에 지속적으로 지원받고 새로운 교육방식에 대한 사회적 관심 속에 빠르게 증가하고 있기 때문이다. 이러한 상황 속에서, 기존에 없었던 서비스의 제공을 통해 기존 사교육 시장에서의 니치마켓으로 기능하게 될 것이다.  
&nbsp;&nbsp;&nbsp;또한 학생의 사교육비는 획기적으로 절감될 것이다. 통계청이 발표(2020년 3월)한 '2019년 사교육비조사 결과' 에 의하면 2019년 고등학생의 사교육비는 6조 2천억 원 규모이다. 서울 강남구 대치동의 한 컨설팅 학원에서는 면접대비 비용으로 144만 원을 청구한다. 이처럼 높은 사교육열에 따라 학생의 비용부담도 증가하는데, 본 서비스를 통해 기존보다 적은 비용으로 대입을 준비할 수 있게 될 것이다.  
&nbsp;&nbsp;&nbsp;마지막으로 공교육의 강화가 일어날 것이다. 전희경 의원이 교육부로부터 받은‘연도별 입시컨설팅 학원 수’자료에 의하면 2015년 67개에 불과했던 입시컨설팅 학원은 2019년 현재 258개로 불어났다. 게다가 학생 수는 매년 줄고 있는 것에 비해, 사교육 참여율은 61.0%, 인당 월평균 사교육비는 36만 5천원으로 꾸준히 증가해 왔다. 본 서비스는 컨설팅 학원을 이용하지 않고도 교사와 함께 입시를 대비할 수 있다는 점에서, 대입에서의 사교육 의존도를 크게 떨어뜨릴 것이다.  
&nbsp;&nbsp;&nbsp;활용방안으로는 먼저 면접이 필요한 모든 학생들에게 평등한 면접 연습 기회를 제공할 수 있다는 것이다. 특히 질병 및 자연재해로 인해 대면으로 면접을 돕기 어려운 교사들에게 비대면으로 도움을 줄 수 있으며, 학생간에도 서로 면접 준비 대비가 가능할 것이다. 더 나아가 대입 준비 뿐만 아니라 취업준비생, 유학생, 이직자 등의 다양한 면접 활용 분야에서도 사용이 가능할 것이다.  

### 6. 참고문헌
강귀석(2020), "메가스터디 교육 기술분석보고서" 3-4쪽, 한국IR협의회  
윤정아(2019), “학생부 관리에만 480만원 … 입시코디 학원 4배”, 문화일보
  
-----------------
## 프로젝트 기간
2020.09 - 2021.09

## Contributor
+ 팀명 : 말하는 감자들
+ 팀장 : 숭실대학교 소프트웨어학부 이아현
+ 팀원 : 숭실대학교 소프트웨어학부 강유진
+ 팀원 : 숭실대학교 소프트웨어학부 박수현
+ 팀원 : 숭실대학교 소프트웨어학부 진혜원
+ 팀원 : 숭실대학교 소프트웨어학부 채예진

## 수상
2020 ETRI 오픈 API 활용사례 공모전 - 가작 수상  
2021 숭실대학교 소프트웨어학부 소프트웨어공모전 - 학장상 수상  
제11회 숭실 캡스톤디자인 경진대회 - 대상 수상  
2021 서울과학기술대학교 공학교육선도센터 창의적 종합설계 경진대회 - 장려상 수상
