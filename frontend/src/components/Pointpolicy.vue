<template>
    <div>
        <String
            label="Name"
            v-model="value.name"
            :editMode="editMode"
        />
        <String
            label="Description"
            v-model="value.description"
            :editMode="editMode"
        />
        <String
            label="PointType"
            v-model="value.pointType"
            :editMode="editMode"
        />
        <Number
            label="Amount"
            v-model="value.amount"
            :editMode="editMode"
        />
        <Boolean
            label="IsActive"
            v-model="value.isActive"
            :editMode="editMode"
        />
        <Date
            label="CreatedAt"
            v-model="value.createdAt"
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
    name: 'Pointpolicy',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "pointpolicies",
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
