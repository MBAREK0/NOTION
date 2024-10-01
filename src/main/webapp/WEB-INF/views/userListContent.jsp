<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="w-full flex justify-end">
    <a href="<%= request.getContextPath() %>/users?action=create" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">Create New User</a>
</div>



<div class="relative overflow-x-auto shadow-md sm:rounded-lg pb-10 py-5">
    <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
        <caption class="px-5 pb-5 text-lg font-semibold text-left rtl:text-right text-gray-900 bg-white dark:text-white dark:bg-gray-800">
            All Users
        </caption>
        <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
        <tr>
            <th scope="col" class="px-8 py-4"> <!-- Increased padding -->
                Username
            </th>
            <th scope="col" class="px-8 py-4">
                First Name
            </th>
            <th scope="col" class="px-8 py-4">
                Last Name
            </th>
            <th scope="col" class="px-8 py-4">
                Email
            </th>
            <th scope="col" class="px-8 py-4">
                Role
            </th>
            <th scope="col" class="px-8 py-4">
                Actions
            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}">
            <tr>
                <td class="px-8 py-4">${user.username}</td> <!-- Increased padding -->
                <td class="px-8 py-4">${user.firstName}</td>
                <td class="px-8 py-4">${user.lastName}</td>
                <td class="px-8 py-4">${user.email}</td>
                <td class="px-8 py-4">${user.role}</td>
                <td class="px-8 py-4">
                    <a href="<%= request.getContextPath() %>/users?action=edit&id=${user.id}" class="text-blue-500">Edit</a>
                    <a href="<%= request.getContextPath() %>/users?action=delete&id=${user.id}" class="text-red-500 ml-3">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>




