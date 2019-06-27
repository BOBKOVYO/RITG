package com.example.practice;

/// <summary>
/// Коллбек  авторизации.
/// <seealso cref="IFiscalCore.DirectIO"/>
/// </summary>
interface IAuth
{
    /// <summary>
    /// Сервисная функция для обмена данными.
    /// Вызывается фискальным ядром.
    /// </summary>
    /// <param name="message">Строка с данными. ФЯ -> ПО</param>
    /// <returns>Ответ на коллбек. ПО -> ФЯ</returns>
    /// <seealso cref="IFiscalCore.DirectIO"/>
    String Exchange(String message);

    /// <summary>
    /// Сервисная функция для передачи целочисленного результата.
    /// Вызывается фискальным ядром.
    /// </summary>
    /// <param name="data">Целочисленный езультат выполнения функции. ФЯ -> ПО</param>
    /// <returns>Ответ на коллбек. ПО -> ФЯ</returns>
    /// <seealso cref="IFiscalCore.DirectIO"/>
    int Result(int data);
}
