<template>
  <div>
    회원정보 등록
    <input v-model="inputData.form.age" placeholder="age"/>
    <input v-model="inputData.form.name" placeholder="name"/>
    <input v-model="inputData.form.gender" placeholder="gender 0:남자, 1:여자"/>
    <input v-model="inputData.form.score" placeholder="score"/>
    <button @click="register">등록</button>
  </div>
<!--  <div>-->
<!--    이름으로 검색-->
<!--    <input v-model="inputData.inputUser" placeholder="이름으로 검색"/>-->
<!--    <button @click="getUserByName">검색</button>-->
<!--  </div>-->

  <InbodyChart :nowUser="inputData.nowUser"/>
</template>

<script>
import {reactive,} from "vue";
import InbodyChart from './InbodyChart'
import axios from "axios";

export default {
  name: "InputData",
  components: {
    InbodyChart
  },
  setup() {
    const inputData = reactive({
      inputUser:'',
      nowUser:'',
      form: {
        id:0,
        age:'',
        name:'',
        gender:'',
        score:'',
      }
    })



    function register() {
      let urlPost = '/main/postUser';
      axios.post(urlPost,{
        id:inputData.form.id,
        age:inputData.form.age,
        name:inputData.form.name,
        gender:inputData.form.gender,
        score:inputData.form.score,
      })
          .then((res) => {
            console.log("hnmmmmmmmmm"+res.data)
          });
    }

    function getUserByName(){
      let urlGet = '/main/getOneUser/'+inputData.inputUser;
      axios.get(urlGet,{})
          .then((res) => {
            inputData.nowUser = res.data;

          });
    }
    return {inputData, register, getUserByName}
  }

}
</script>

<style scoped>

</style>