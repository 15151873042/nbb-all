import Vue from 'vue'
import store from '@/store'
import DataDict from '@/utils/dict'
import { getDicts } from '@/api/system/dict/data'
import {isBlank } from "@/utils/ruoyi";


function searchDictByKey(dictList, key) {
  if (isBlank(key)) {
    return null
  }

  try {
    for (let i = 0; i < dictList.length; i++) {
      if (dictList[i].key == key) {
        return dictList[i].value
      }
    }
  } catch (e) {
    return null
  }
}


export default {
  apply() {
    Vue.use(DataDict, {
      metas: {
        '*': {
          labelField: 'dictLabel',
          valueField: 'dictValue',
          request(dictMeta) {
            const storeDict = searchDictByKey(store.getters.dict, dictMeta.type)
            if (storeDict) {
              return new Promise(resolve => { resolve(storeDict) })
            } else {
              return new Promise((resolve, reject) => {
                getDicts(dictMeta.type).then(res => {
                  store.dispatch('dict/setDict', { key: dictMeta.type, value: res.data })
                  resolve(res.data)
                }).catch(error => {
                  reject(error)
                })
              })
            }
          },
        },
      },
    })
  }
}