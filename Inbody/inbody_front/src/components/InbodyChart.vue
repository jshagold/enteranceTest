<template>
  <div class="hello">
    <div>
      이름으로 검색
      <input v-model="data.inputUser" placeholder="이름으로 검색"/>
      <button @click="getUserByName">검색</button>
    </div>

    <div>내 인바디 랭킹</div>
    <div>
      <div v-if="data.nowUser.gender == 0">
        남성,<span> {{data.nowUser.age-5}} ~ {{data.nowUser.age+5}}세 기준 </span>
      </div>
      <div v-else-if="data.nowUser.gender == 1">
        여성,<span> {{data.nowUser.age-5}} ~ {{data.nowUser.age+5}}세 기준 </span>
      </div>
      <div v-else>
        회원정보를 입력해 주세요
      </div>
    </div>
    <canvas id="c" width="800" height="300"
            style="border:1px solid #d3d3d3;">
      Your browser does not support the canvas element.
    </canvas>
    <div>
      평균( {{data.mean}}점 )
    </div>
    <div>
      <div>
        <div>내 인바디 점수</div>
        <div>{{data.nowUser.score}}점</div>
      </div>
      <div>
        <div>랭킹</div>
        <div>상위 {{data.userRank}}%</div>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted, onUpdated, reactive} from 'vue'
import axios from 'axios'

export default {
  name: 'InbodyChart',
  props: [],
  setup() {
    let data = reactive({
      nowUser:"",
      mean: '',
      user: '',
      userRank: '',
      // db의 모든 사용자
      list: [],
      // 조건에 맞는 사용자
      realList:[],
      scoreList: [],
      inputUser:'',
    })

    let urlGet = '/main/getUserList';
    axios.get(urlGet,{})
        .then((res) => {
          data.list = res.data;
        });

    onMounted(() => {})
    onUpdated(() => {})

    // 입력받은 유저의 정보 받아오기
    function getUserByName(){
      let urlGet = '/main/getOneUser/'+data.inputUser;
      axios.get(urlGet,{})
          .then((res) => {
            data.nowUser = res.data;
            data.realList = [];
            for(let users in data.list){
              if(data.list[users].age <= data.nowUser.age+5 && data.list[users].age >= data.nowUser.age-5 && data.list[users].gender == data.nowUser.gender){
                data.realList.push(data.list[users]);
              }
            }
            drawfunc()
          });
    }

    function drawfunc(){
      // 회원들의 인바디점수 총합
      let sumScore = 0;
      for(let users in data.realList) {
        sumScore = sumScore+data.realList[users].score;
      }

      // 회원들의 인바디 점수 리스트화, 정렬
      let scoreList = [];
      for(let users in data.realList) {
        scoreList.push(data.realList[users].score)
      }
      scoreList.sort((a,b)=>{return a-b})
      // 회원들의 평균 점수
      data.mean = Math.round(sumScore/data.realList.length);
      let value = [];
      let userIndex = 15;

      // 입력된 사용자의 데이터들로 그려진 막대그래프 차트 데이터 정리
      //
      // const minScore = scoreList[0];
      // const maxScore = scoreList[scoreList.length-1]
      // const difference = (maxScore - minScore) / 16;
      // let countPeople = 0;
      // let Standard = minScore+difference;
      //
      // for(let userScore in scoreList) {
      //   // 사용자 상위 백분율 표기
      //   if(scoreList[userScore] == data.nowUser.score){
      //     data.userRank = Math.round((scoreList.length - userScore)/scoreList.length * 100);
      //   }
      //   // 차트 구분, 각 막대를 % 비율로 표시(최대 100%)
      //   if(scoreList[userScore] < Standard){
      //     countPeople = countPeople+1;
      //   }else {
      //     value.push(Math.round(countPeople/scoreList.length * 100));
      //     countPeople = 1;
      //     Standard = Standard + difference
      //   }
      // }

      for(let userScore in scoreList) {
        // 사용자 상위 백분율 표기
        if(scoreList[userScore] == data.nowUser.score){
          data.userRank = Math.round((scoreList.length - userScore)/scoreList.length * 100);
        }
      }
      // 정규분포 형태의 막대그래프 차트데이터
      value = [5,10,17,36,47,60,75,88,88,75,60,47,36,17,10,5];
      // 현재 사용자의 위치 막대그래프에 표시
      for(let i=1; i<=16; i++){
        if(data.userRank>=i*6.25 && data.userRank<(i+1)*6.25){
          userIndex = 15-i;
        }
      }

      // 막대 그래프 차트 그리기
      let canvas = document.getElementById("c");
      let ctx = canvas.getContext("2d");
      ctx.font = "30px Arial";
      let width = canvas.clientWidth;
      let height = canvas.clientHeight;
      let position = {
        min_x : width * 0.05,
        max_x : width * 0.9,
        max_y : height,
        min_y : height * 0.1
      }
      let wid = 10;
      ctx.beginPath();

      value.forEach( (data, idx) => {
        //x좌표의 시작은 최소x값에 비율을 더해준 값
        let devide = idx / value.length ;
        if(idx == userIndex){
          ctx.strokeStyle = 'red';
          ctx.fillStyle = 'red';
        }
        else {
          ctx.strokeStyle = 'gray';
          ctx.fillStyle = 'gray';
        }
        ctx.fillRect(
            position.min_x + position.max_x*devide,
            position.max_y* ( 1-(data/100) ),
            wid,
            position.max_y - position.max_y* ( 1-(data/100) )
        );
      });
    }

    return {
      data,
      getUserByName,
      drawfunc
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
