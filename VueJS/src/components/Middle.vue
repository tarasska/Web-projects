<template>
    <div class="middle">
        <Sidebar :users="users" :posts="posts"/>
        <main>
            <Index :users="users" :posts="posts" :comments="comments" v-if="page === 'Index'"/>
            <Enter v-if="page === 'Enter'"/>
            <Register v-if="page === 'Register'"/>
            <Users v-if="page === 'Users'" :users="users"/>
            <Post v-if="page === 'Post'" :users="users" :post="post"/>
            <PostPage v-if="page === 'PostPage'" :users="users" :comments="comments" :post="post"/>
            <AddPost v-if="page === 'AddPost'"/>
            <EditPost v-if="page === 'EditPost'"/>
        </main>
    </div>
</template>
<script>
    import Index from './middle/Index';
    import Enter from './middle/Enter';
    import Register from './middle/Register';
    import AddPost from './middle/AddPost';
    import Sidebar from './Sidebar';
    import EditPost from "./middle/EditPost";
    import Users from "./middle/Users";
    import Post from "./middle/Post";
    import PostPage from "./middle/PostPage";

    export default {
        name: "Middle",
        props: ['users', 'posts', 'comments'],
        data: function () {
            return {
                page: "Index",
                isSinglePost: false
            }
        },
        components: {
            PostPage,
            Post,
            Users,
            EditPost,
            Index,
            Enter,
            Register,
            Sidebar,
            AddPost,
        }, beforeCreate() {
            this.$root.$on("onChangePage", (page) => {
                this.page = page;
            });
            this.$root.$on("onViewPost", (post) => {
                this.page = 'PostPage';
                this.post = post;
             })
        }
    }
</script>

<style scoped>

</style>
