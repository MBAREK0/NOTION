<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Page Title</title>
    <!-- Flowbite CSS -->
    <link href="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@3.3.3/dist/tailwind.min.css" rel="stylesheet">
    <%--    <link rel="stylesheet" href="<%= request.getContextPath() %>/path/to/your/custom/styles.css" />--%>
</head>
<body>




<jsp:include page="../layout/aside.jsp"    />

<div class="p-4 sm:ml-64">
    <div class="p-4 rounded-lg dark:border-gray-700">
        <div class=" mb-4">
            <%--content--%>


            <div class="w-full flex justify-end">
                <a href="<%= request.getContextPath() %>/tags?action=create" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">Create New Tag</a>
            </div>

            <div class="width-full flex justify-center ">
                <c:if test="${not empty sessionScope.errorMessage}">

                    <div class="p-4 mb-4 text-sm text-red-800 rounded-lg bg-red-50 dark:bg-gray-800 dark:text-red-400" role="alert">
                            ${sessionScope.errorMessage}
                    </div>
                    <c:remove var="errorMessage" scope="session" />
                </c:if>
                <c:if test="${not empty sessionScope.message}">

                    <div class="p-4 mb-4 text-sm text-green-800 rounded-lg bg-green-50 dark:bg-gray-800 dark:text-green-400" role="alert">
                            ${sessionScope.message}
                    </div>
                    <c:remove var="message" scope="session" />
                </c:if>
            </div>
                <c:if test="${not empty tags}">
                    <div class="relative overflow-x-auto shadow-md sm:rounded-lg mt-10">
                        <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                            <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                            <tr>
                                <th scope="col" class="px-6">Tag Name</th>
                                <th scope="col" class="px-6">Edit</th>
                                <th scope="col" class="px-6">Delete</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="tag" items="${tags}">
                                <tr class="odd:bg-white odd:dark:bg-gray-900 even:bg-gray-50 even:dark:bg-gray-800 border-b dark:border-gray-700">
                                    <td class="px-6 py-2 font-medium text-gray-900 dark:text-white">
                                            ${tag.name}
                                    </td>
                                    <td class="px-6 py-2">
                                        <a href="<%= request.getContextPath() %>/tags?action=edit&id=${tag.id}" class="text-blue-500">Edit</a>
                                    </td>
                                    <td class="px-6 py-2">
                                        <a href="<%= request.getContextPath() %>/tags?action=delete&id=${tag.id}" class="text-red-500">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
                <c:if test="${empty tags}">
                    <div class="w-full text-center text-black">
                        No Tags found
                    </div>
                </c:if>
        </div>
    </div>
</div>





<script src="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.js"></script>
</body>
</html>
