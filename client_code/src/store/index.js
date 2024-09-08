import { createStore } from 'vuex'

export default createStore({
  state: {
	  audioList:[],
	  audioIndex:-1
  },
  getters: {
  },
  mutations: {
	  SET_audio: (state, audioList) => {
	    state.audioList = audioList
	  },
	  SET_audioIndex: (state, audioIndex) => {
	    state.audioIndex = audioIndex
	  }
  },
  actions: {
	  setAudio({ commit }) {
	    return new Promise(resolve => {
	      commit('SET_audio', [])
	      commit('SET_audioIndex', -1)
	    })
	  },
	  setList({commit},arr){
		  commit('SET_audio',arr)
	  },
	  setOnceIndex({commit,state}){
		  commit('SET_audioIndex', state.audioList.length - 1)
	  },
	  setIndex({commit},index){
		  console.log(1123,index)
		  commit('SET_audioIndex',index)
	  },
	  delAudio({commit,state},index){
		  if(index == state.audioIndex){
			  if(state.audioIndex==0){
				  
			  }else{
				  commit('SET_audioIndex',index - 1)
			  }
		  }else if(index<state.audioIndex){
			  commit('SET_audioIndex',state.audioIndex - 1)
		  }
		  state.audioList.splice(index,1)
	  }
  },
  modules: {
  }
})
