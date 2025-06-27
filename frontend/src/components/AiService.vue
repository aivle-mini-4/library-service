<template>
    <div>
        <Number
            label="ManuscriptId"
            v-model="value.manuscriptId"
            :editMode="editMode"
        />
        <String
            label="Model"
            v-model="value.model"
            :editMode="editMode"
        />
        <String
            label="ApiKey"
            v-model="value.apiKey"
            :editMode="editMode"
        />
        <v-row class="ma-0 pa-0">
            <v-spacer></v-spacer>
            <v-btn width="64px" color="primary" @click="save">
                저장
            </v-btn>
        </v-row>
    </div>
</template>


<script>
import BaseEntity from './base-ui/BaseEntity.vue'

export default {
    name: 'AiService',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "aiServices",
        value: {
        }
    }),
    created(){
    },
    computed:{
    },
    methods: {
    },
}
</script>
