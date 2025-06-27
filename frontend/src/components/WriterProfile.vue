<template>
    <div>
        <String
            label="Name"
            v-model="value.name"
            :editMode="editMode"
        />
        <String
            label="Email"
            v-model="value.email"
            :editMode="editMode"
        />
        <String
            label="Roles"
            v-model="value.roles"
            :editMode="editMode"
        />
        <String
            label="BasicInformation"
            v-model="value.basicInformation"
            :editMode="editMode"
        />
        <String
            label="SelfIntroduction"
            v-model="value.selfIntroduction"
            :editMode="editMode"
        />
        <String
            label="Portfolio"
            v-model="value.portfolio"
            :editMode="editMode"
        />
        <Date
            label="UpdatedAt"
            v-model="value.updatedAt"
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
    name: 'WriterProfile',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "writerProfiles",
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
