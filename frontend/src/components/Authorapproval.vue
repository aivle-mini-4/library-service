<template>
    <div>
        <Number
            label="Authorid"
            v-model="value.authorid"
            :editMode="editMode"
        />
        <String
            label="State"
            v-model="value.state"
            :editMode="editMode"
        />
        <Date
            label="AppliedAt"
            v-model="value.appliedAt"
            :editMode="editMode"
        />
        <Date
            label="ResultAt"
            v-model="value.resultAt"
            :editMode="editMode"
        />
        <Date
            label="RejectedAt"
            v-model="value.rejectedAt"
            :editMode="editMode"
        />
        <Number
            label="AdminId"
            v-model="value.adminId"
            :editMode="editMode"
        />
        <String
            label="Reason"
            v-model="value.reason"
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
    name: 'Authorapproval',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "authorapprovals",
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
