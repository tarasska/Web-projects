<template>
    <div class="datatable">
        <div class="caption">Users list</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Name</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="user in users" v-bind:key="user.id">
                <td>{{user.id}}</td>
                <td>{{user.login}}</td>
                <td>{{user.name}}</td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    export default {
        name: "Users",
        props: ['users']
    }
</script>

<style scoped>
    .datatable {
        border-top-left-radius: var(--border-radius);
        border-top-right-radius: var(--border-radius);
    }

    .datatable .caption {
        background-color: var(--border-color);
        border-top-left-radius: var(--border-radius);
        border-top-right-radius: var(--border-radius);
        padding: 0.25rem;
    }

    .datatable table {
        width: 100%;
        text-align: center;
        border-collapse: collapse;
    }

    .datatable table tr th {
        height: 2rem;
        border: 1px solid var(--border-color);
    }

    .datatable table tr td {
        height: 2rem;
        border: 1px solid var(--border-color);
    }

    .datatable table tbody tr:nth-child(2n + 1) {
        background-color: rgb(240, 240, 240);
    }
</style>