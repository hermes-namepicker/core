<template>
    <main class="outerClassManagetContainer">
        <div class="classManagerContainer">
            
            <ul class="schoolClassList">
                <li><button class="addClassButton">Add Class</button></li>
                <li v-for="schoolClass in schoolClasses" class="classListItem">
                    {{schoolClass}} 
                    <button class="editButton" @click="onEdit">Edit</button>
                </li>
            </ul>
        </div>
    </main>
</template>

<script setup>
const schoolClasses = ref(["INA22aL", "class2"])

const onEdit = () => {
    
}
</script>