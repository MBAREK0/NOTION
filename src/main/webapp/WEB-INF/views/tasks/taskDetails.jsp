<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

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


                <ol class="relative text-gray-500 border-s border-gray-200 dark:border-gray-700 dark:text-gray-400">
                    <li class="mb-10 ms-6">
                        <span class="absolute flex items-center justify-center w-8 h-8 bg-green-200 rounded-full -start-4 ring-4 ring-white dark:ring-gray-900 dark:bg-green-900">
                            <svg class="w-3.5 h-3.5 text-green-500 dark:text-green-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 16 12">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M1 5.917 5.724 10.5 15 1.5"/>
                            </svg>
                        </span>
                        <h3 class="font-medium leading-tight">Task Title</h3>
                        <p class="text-sm">${task.title}</p>
                    </li>

                    <li class="mb-10 ms-6">
                        <span class="absolute flex items-center justify-center w-8 h-8 bg-gray-100 rounded-full -start-4 ring-4 ring-white dark:ring-gray-900 dark:bg-gray-700">
                           <svg class="w-3.5 h-3.5 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 18 20">
                                <path d="M16 1h-3.278A1.992 1.992 0 0 0 11 0H7a1.993 1.993 0 0 0-1.722 1H2a2 2 0 0 0-2 2v15a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V3a2 2 0 0 0-2-2Zm-3 14H5a1 1 0 0 1 0-2h8a1 1 0 0 1 0 2Zm0-4H5a1 1 0 0 1 0-2h8a1 1 0 1 1 0 2Zm0-5H5a1 1 0 0 1 0-2h2V2h4v2h2a1 1 0 1 1 0 2Z"/>
                            </svg>
                        </span>
                        <h3 class="font-medium leading-tight">Description</h3>
                        <p class="text-sm">${task.description}</p>
                    </li>

                    <li class="mb-10 ms-6 flex justify-start items-center">
                        <span class="absolute flex items-center justify-center w-8 h-8 bg-gray-100 rounded-full -start-4 ring-4 ring-white dark:ring-gray-900 dark:bg-gray-700">
                            <svg class="w-3.5 h-3.5 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 24 24">
                                <path d="M12 0C5.37 0 0 5.37 0 12s5.37 12 12 12 12-5.37 12-12S18.63 0 12 0zm1.5 18h-3v-8h3v8zm0-10h-3V5h3v3z"/>
                            </svg>
                        </span>
                        <c:choose>
                            <c:when test="${task.status == 'pending'}">
                                <span class="bg-blue-100 text-blue-800 text-xs font-medium me-2 px-2.5 py-0.5 rounded dark:bg-blue-900 dark:text-blue-300">Pending</span>
                            </c:when>
                            <c:when test="${task.status == 'in_progress'}">
                                <span class="bg-gray-100 text-gray-800 text-xs font-medium me-2 px-2.5 py-0.5 rounded dark:bg-gray-700 dark:text-gray-300">In Progress</span>
                            </c:when>
                            <c:when test="${task.status == 'completed'}">
                                <span class="bg-green-100 text-green-800 text-xs font-medium me-2 px-2.5 py-0.5 rounded dark:bg-green-900 dark:text-green-300">Completed</span>
                            </c:when>
                            <c:when test="${task.status == 'overdue'}">
                                <span class="bg-red-100 text-red-800 text-xs font-medium me-2 px-2.5 py-0.5 rounded dark:bg-red-900 dark:text-red-300">Overdue</span>
                            </c:when>
                            <c:otherwise>
                                <span class="bg-gray-100 text-gray-800 text-xs font-medium me-2 px-2.5 py-0.5 rounded dark:bg-gray-700 dark:text-gray-300">Unknown</span>
                            </c:otherwise>
                        </c:choose>
                    </li>

                    <li class="ms-6">
                        <span class="absolute flex items-center justify-center w-8 h-8 bg-gray-100 rounded-full -start-4 ring-4 ring-white dark:ring-gray-900 dark:bg-gray-700">
                           <svg class="w-3.5 h-3.5 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 64 64">
                                <circle cx="32" cy="32" r="5.629" />
                                <path d="M32 34.816c-1.555 0-2.816-1.261-2.816-2.816V17.2c0-1.555 1.261-2.816 2.816-2.816 1.555 0 2.816 1.261 2.816 2.816V32c0 1.555-1.261 2.816-2.816 2.816z" />
                                <path d="M24.942 41.874c-.721 0-1.441-.275-1.991-.825-1.1-1.1-1.1-2.883 0-3.982l7.058-7.058c1.099-1.1 2.883-1.1 3.982 0 1.1 1.1 1.1 2.883 0 3.982l-7.058 7.058c-.55.55-1.27.825-1.991.825z" />
                                <path d="M32 59.5C16.837 59.5 4.5 47.164 4.5 32 4.5 16.836 16.837 4.5 32 4.5S59.5 16.836 59.5 32C59.5 47.164 47.163 59.5 32 59.5zM32 10.132c-12.058 0-21.868 9.81-21.868 21.868 0 12.058 9.81 21.868 21.868 21.868S53.868 44.058 53.868 32C53.868 19.942 44.058 10.132 32 10.132z" />
                            </svg>
                        </span>
                        <h3 class="font-medium leading-tight">Deadline</h3>
                       <div class="flex justify-start items-center gap-1">
                           <p class="text-sm">${formattedStartDate}</p>
                           <p class="text-blue-400">to</p>
                           <p class="text-sm">${formattedEndDate}</p>
                       </div>
                    </li>

                </ol>
        </div>
    </div>
</div>





<script src="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.js"></script>
</body>
</html>
